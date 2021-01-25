package com.deckbop.api.data;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public interface IDeckDatasource {
    SqlRowSet getDeckById(long deck_id);
    SqlRowSet getCardsByDeckId(long deck_id);
    SqlRowSet getDeckIdsByUserId(long userId);
    Long insertDeck(String deckName, long userId);
    void deleteDeck(long deck_id);
    void deleteCardsFromDeck(long deck_id);
    void updateDeckTable(String deckName, long deck_id);
    void addCardsToDeck(String sql);
}
