package com.deckbop.api.data;

import com.deckbop.api.model.Card;
import com.deckbop.api.model.Deck;

import java.util.List;

public interface IDeckDatasource {
    Deck getDeckById(long deck_id);
    List<Card> getCardsByDeckId(long deck_id);
    List<Deck> getDecksByUserId(long user_id);
    Long getNumDeckNameCollisions(long userId, String deckName, long deckId);
    Long insertDeck(String deckName, long userId);
    void deleteDeck(long deck_id);
    void deleteCardsFromDeck(long deck_id);
    void updateDeckTable(String deckName, long deck_id);
    void addCardsToDeck(String sql);
}
