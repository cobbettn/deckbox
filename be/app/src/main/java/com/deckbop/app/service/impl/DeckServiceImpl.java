package com.deckbop.app.service.impl;

import com.deckbop.app.controller.request.DeckRequest;
import com.deckbop.app.controller.response.DeckResponse;
import com.deckbop.app.dao.DeckDAO;
import com.deckbop.app.service.DeckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class DeckServiceImpl implements DeckService {

    @Autowired
    DeckDAO deckDAO;

    @Autowired
    LoggingServiceImpl loggingService;

    @Override
    public void createDeck(DeckRequest request) {
        try {
            deckDAO.createDeck(request);
        }
        catch (DataAccessException e) {
            loggingService.error("SQL error while creating deck");
            throw e;
        }
    }

    @Override
    public DeckResponse getDeck(long deck_id) {
        DeckResponse response =  null;
        try {
            Optional<DeckResponse> deck =  deckDAO.getDeck(deck_id);
            if (deck.isPresent()) {
                response = deck.get();
            }
        }
        catch (Exception e) {
            loggingService.error("Error while retrieving deck");
            throw e;
        }
        return response;
    }

    @Override
    public void updateDeck(DeckRequest request, long id) {
        try {
            deckDAO.updateDeck(request, id);
        }
        catch (Exception e) {
            loggingService.error("SQL error in updateDeck");
            throw e;
        }
    }

    @Override
    public void deleteDeck(long id) {
        try {
            deckDAO.deleteDeck(id);
        }
        catch (DataAccessException e) {
            loggingService.error("SQL error in deleteDeck");
            throw e;
        }
    }

    @Override
    public List<Long> getDeckIdsByUserId(long userId) {
        try {
            return deckDAO.getDeckIdsByUserId(userId);
        }
        catch (DataAccessException e) {
            loggingService.error("SQL error in getDeckIdsByUserId");
            throw e;
        }
    }

    @Override
    public void deleteUserDecks(long userId) {
        try {
            List<Long> list = this.getDeckIdsByUserId(userId);
            list.forEach(this::deleteDeck);
        }
        catch (DataAccessException e) {
            loggingService.error("SQL error in deleteUserDecks");
            throw e;
        }
    }

}
