package com.deckbop.api.service;

import com.deckbop.api.controller.request.DeckRequest;
import com.deckbop.api.controller.response.DeckResponse;
import com.deckbop.api.data.dao.DeckDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeckService  {

    @Autowired
    DeckDAO deckDAO;

    @Autowired
    LoggingService loggingService;

    public void createDeck(DeckRequest request) {
        try {
            deckDAO.createDeck(request);
        }
        catch (DataAccessException e) {
            loggingService.error(DeckService.class, "SQL error while creating deck");
            throw e;
        }
    }

    public DeckResponse getDeck(long deck_id) {
        DeckResponse response =  null;
        try {
            Optional<DeckResponse> deck =  deckDAO.getDeck(deck_id);
            if (deck.isPresent()) {
                response = deck.get();
            }
        }
        catch (Exception e) {
            loggingService.error(DeckService.class,"Error while retrieving deck");
            throw e;
        }
        return response;
    }

    public void updateDeck(DeckRequest request, long id) {
        try {
            deckDAO.updateDeck(request, id);
        }
        catch (Exception e) {
            loggingService.error(DeckService.class,"SQL error in updateDeck");
            throw e;
        }
    }

    public void deleteDeck(long id) {
        try {
            deckDAO.deleteDeck(id);
        }
        catch (DataAccessException e) {
            loggingService.error(DeckService.class,"SQL error in deleteDeck");
            throw e;
        }
    }

    public List<Long> getDeckIdsByUserId(long userId) {
        try {
            return deckDAO.getDeckIdsByUserId(userId);
        }
        catch (DataAccessException e) {
            loggingService.error(DeckService.class,"SQL error in getDeckIdsByUserId");
            throw e;
        }
    }

    public void deleteUserDecks(long userId) {
        try {
            List<Long> list = this.getDeckIdsByUserId(userId);
            list.forEach(this::deleteDeck);
        }
        catch (DataAccessException e) {
            loggingService.error(DeckService.class,"SQL error in deleteUserDecks");
            throw e;
        }
    }

}
