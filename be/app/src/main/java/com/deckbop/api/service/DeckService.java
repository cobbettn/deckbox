package com.deckbop.api.service;

import com.deckbop.api.data.SQLTemplates;
import com.deckbop.api.data.dao.impl.DeckDatabaseDAO;
import com.deckbop.api.exception.CreateDeckException;
import com.deckbop.api.exception.DeckNameExistsException;
import com.deckbop.api.model.Card;
import com.deckbop.api.model.Deck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeckService  {

    @Autowired
    DeckDatabaseDAO deckDatabaseDAO;

    @Autowired
    LoggingService loggingService;

    @Autowired
    ScryfallService scryfallService;

    public void createDeck(Deck request) throws CreateDeckException, DeckNameExistsException {
        if (this.validateDeckRequest(request, -1)) {
            try {
                String deckName = request.getName();
                long userId = request.getUserId();
                Long deck_id = deckDatabaseDAO.insertDeck(deckName, userId);
                if (deck_id > 0) {
                    String cardValues = this.getCardDataSQL(request, deck_id);
                    deckDatabaseDAO.addCardsToDeck(SQLTemplates.addCardsToDeck + cardValues);

                } else {
                    loggingService.error(this, "SQL ERROR could not create deck.");
                    throw new CreateDeckException("SQL ERROR could not create deck.");
                }
            } catch (DataAccessException e) {
                loggingService.error(this, "SQL error while creating deck");
                throw e;
            }
        } else {
            throw new DeckNameExistsException("Could not create, deck name exists");
        }
    }

    public Deck getDeck(long deck_id) throws Exception {
        Deck deck;
        try {
            Deck deckTable = deckDatabaseDAO.getDeckById(deck_id);
            List<Card> cardList = deckDatabaseDAO.getCardsByDeckId(deck_id);
            deck = new Deck(deck_id, deckTable.getUserId(), deckTable.getName(), cardList);
            deck.setScryFallCards(scryfallService.fetchCardList(deck.getCards()));
        }
        catch (EmptyResultDataAccessException e) {
            loggingService.error(this,"deck with id: " + deck_id + " does not exist");
            throw new Exception("deck does not exist");
        }
        catch (DataAccessException e) {
            loggingService.error(this,"SQL Error while retrieving deck");
            throw e;
        }
        return deck;
    }

    public Deck updateDeck(Deck request, long deck_id) throws DeckNameExistsException {
        if (this.validateDeckRequest(request, deck_id)) {
            try {
                deckDatabaseDAO.updateDeckTable(request.getName(), deck_id);
                deckDatabaseDAO.deleteCardsFromDeck(deck_id);
                String cardValues = this.getCardDataSQL(request, deck_id);
                deckDatabaseDAO.addCardsToDeck(SQLTemplates.addCardsToDeck + cardValues);
                request.setScryFallCards(scryfallService.fetchCardList(request.getCards()));
            } catch (Exception e) {
                loggingService.error(this, "SQL error in updateDeck");
                throw e;
            }
        } else {
            throw new DeckNameExistsException("Could not update, deck name exists");
        }
        return request;
    }

    public void deleteDeck(long deck_id) {
        try {
            deckDatabaseDAO.deleteCardsFromDeck(deck_id);
            deckDatabaseDAO.deleteDeck(deck_id);
        }
        catch (DataAccessException e) {
            loggingService.error(this,"SQL error in deleteDeck");
            throw e;
        }
    }

    public List<Deck> getUserDecks(long userId) {
        List<Deck> decks;
        try {
            decks = deckDatabaseDAO.getDecksByUserId(userId);
            decks.forEach(deck -> {
                List<Card> cardList = deckDatabaseDAO.getCardsByDeckId(deck.getId());
                deck.setCards(cardList);
                deck.setScryFallCards(scryfallService.fetchCardList(cardList));
            });
        }
        catch (DataAccessException e) {
            loggingService.error(this,"SQL error in getUserDecks: " + e.getMessage());
            throw e;
        }
        return decks;
    }

    public void deleteUserDecks(long userId) {
        try {
            this.getUserDeckIds(userId).forEach(this::deleteDeck);
        }
        catch (DataAccessException e) {
            loggingService.error(this,"SQL error in deleteUserDecks");
            throw e;
        }
    }

    private String getCardDataSQL(Deck request, long deckId){
        List<Card> cards = request.getCards();
        String[] values = new String[cards.size()];
        for (int i = 0; i < values.length; i++) {
            Card c = cards.get(i);
            String cardId = c.getCard_id();
            int cardQuantity = c.getCard_quantity();
            values[i] =  "(" + deckId + ", '" + cardId + "', " + cardQuantity + ")";
        }
        return String.join(", ", values);
    }

    private List<Long> getUserDeckIds(long userId) {
        List<Long> deckIds = new ArrayList<>();
        this.getUserDecks(userId).forEach(deck -> deckIds.add(deck.getId()));
        return deckIds;
    }

    private boolean validateDeckRequest(Deck request, long deckId) {
        return 0 == deckDatabaseDAO.getNumDeckNameCollisions(request.getUserId(), request.getName(), deckId);
    }
}
