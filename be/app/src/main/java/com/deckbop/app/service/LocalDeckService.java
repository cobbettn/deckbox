package com.deckbop.app.service;

import com.deckbop.app.controller.request.DeckRequest;
import com.deckbop.app.controller.response.DeckGetResponse;
import com.deckbop.app.dao.DeckDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class LocalDeckService implements DeckService{

    @Autowired
    DeckDAO deckDAO;

    @Override
    public void createDeck(DeckRequest request) {
        deckDAO.createDeck(request);
    }

    @Override
    public Optional<DeckGetResponse> getDeck(long deck_id) {
        return deckDAO.getDeck(deck_id);
    }

    @Override
    public void deleteDeck(long id) {
        deckDAO.deleteDeck(id);
    }

    @Override
    public Optional<List<Long>> getDeckIdsByUserId(long userId) {
        return deckDAO.getDeckIdsByUserId(userId);
    }

    @Override
    public void deleteUserDecks(long userId) {
        deckDAO.deleteUserDecks(userId);
    }

    @Override
    public ResponseEntity<?> updateDeck(DeckRequest request, long id) {
        return deckDAO.updateDeck(request, id);
    }
}
