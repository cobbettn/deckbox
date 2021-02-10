package com.deckbop.api.service;

import com.deckbop.api.controller.request.DeckRequest;
import com.deckbop.api.controller.response.DeckResponse;
import com.deckbop.api.data.IUserDatasource;
import com.deckbop.api.data.SQLTemplates;
import com.deckbop.api.data.dao.impl.DeckDatabaseDAO;
import com.deckbop.api.data.dao.impl.UserDatabaseDAO;
import com.deckbop.api.exception.CreateDeckException;
import com.deckbop.api.exception.DeckNameExistsException;
import com.deckbop.api.model.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DeckService  {

    @Autowired
    DeckDatabaseDAO deckDatabaseDAO;

    @Autowired
    LoggingService loggingService;

    public void createDeck(DeckRequest request) throws CreateDeckException, DeckNameExistsException {
        if (validateDeckRequest(request)) {
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
            throw new DeckNameExistsException("Could not update, deck name exists");
        }
    }

    public DeckResponse getDeck(long deck_id) {
        DeckResponse response = null;
        try {
            Optional<DeckResponse> deck = this.getDeckResponse(deck_id);
            if (deck.isPresent()) {
                response = deck.get();
            }
        }
        catch (Exception e) {
            loggingService.error(this,"Error while retrieving deck");
            throw e;
        }
        return response;
    }

    private Optional<DeckResponse> getDeckResponse(long deck_id) {
        DeckResponse deck = null;
        SqlRowSet results  = deckDatabaseDAO.getDeckById(deck_id);
        if (results.next()) {
            String deck_name = results.getString("deck_name");
            int user_id = results.getInt("user_id");
            results = deckDatabaseDAO.getCardsByDeckId(deck_id);
            List<Card> cardList = new ArrayList<>();
            while (results.next()) {
                String card_id = results.getString("card_id");
                int card_qty = results.getInt("card_quantity");
                Card card = new Card(card_id, card_qty);
                cardList.add(card);
            }
            deck = new DeckResponse(deck_name, cardList, user_id, deck_id);
        }
        return Optional.ofNullable(deck);
    }

    public void updateDeck(DeckRequest request, long deck_id) throws DeckNameExistsException {
        if (validateDeckRequest(request)) {
            try {
                deckDatabaseDAO.updateDeckTable(request.getName(), deck_id);
                deckDatabaseDAO.deleteCardsFromDeck(deck_id);
                String cardValues = this.getCardDataSQL(request, deck_id);
                deckDatabaseDAO.addCardsToDeck(SQLTemplates.addCardsToDeck + cardValues);
            } catch (Exception e) {
                loggingService.error(this, "SQL error in updateDeck");
                throw e;
            }
        } else {
            throw new DeckNameExistsException("Could not update, deck name exists");
        }
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

    private List<Long> getDeckIdsByUserId(long userId) {
        try {
            SqlRowSet results = deckDatabaseDAO.getDeckIdsByUserId(userId);
            List<Long> list = new ArrayList<>();
            while (results.next()) {
                list.add(results.getLong("deck_id"));
            }
            return list;
        }
        catch (DataAccessException e) {
            loggingService.error(this,"SQL error in getDeckIdsByUserId");
            throw e;
        }
    }

    public void deleteUserDecks(long userId) {
        try {
            List<Long> list = this.getDeckIdsByUserId(userId);
            list.forEach(this::deleteDeck);
        }
        catch (DataAccessException e) {
            loggingService.error(this,"SQL error in deleteUserDecks");
            throw e;
        }
    }

    private String getCardDataSQL(DeckRequest request, long deckId){
        List<Card> cards = request.getCardList();
        String[] values = new String[cards.size()];
        for (int i = 0; i < values.length; i++) {
            Card c = cards.get(i);
            String cardId = c.getCard_id();
            int cardQuantity = c.getCard_quantity();
            values[i] =  "(" + deckId + ", '" + cardId + "', " + cardQuantity + ")";
        }
        return String.join(", ", values);
    }

    private boolean validateDeckRequest(DeckRequest request){
        return !deckDatabaseDAO.checkDeckInDeckTable(request.getUserId(), request.getName()).next();
    }
}
