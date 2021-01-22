package com.deckbop.app.service;

import com.deckbop.app.controller.request.DeckRequest;
import com.deckbop.app.controller.response.DeckGetResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface DeckService {
    public void createDeck(DeckRequest request);
    public Optional<DeckGetResponse> getDeck(long deck_id);
    public void deleteDeck(long id);
    public Optional<List<Long>> getDeckIdsByUserId(long userId);
    public void deleteUserDecks(long userId);
    public ResponseEntity<?> updateDeck(DeckRequest request, long id);
}
