package com.deckbop.app.service;

import com.deckbop.app.controller.request.DeckRequest;
import com.deckbop.app.controller.response.DeckResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DeckService {
    public void createDeck(DeckRequest request);
    public DeckResponse getDeck(long deck_id);
    public void deleteDeck(long id);
    public List<Long> getDeckIdsByUserId(long userId);
    public void deleteUserDecks(long userId);
    public void updateDeck(DeckRequest request, long id);
}
