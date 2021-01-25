package com.deckbop.api.data.dao;

import com.deckbop.api.controller.request.DeckRequest;
import com.deckbop.api.controller.response.DeckResponse;
import com.deckbop.api.model.Card;
import com.deckbop.api.service.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class DeckDAO {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    LoggingService loggingService;
    
    public void createDeck(DeckRequest request) throws DataAccessException {
        int deckId = insertDeck(request);
        addCardsToDeck(request, deckId);
    }

    public Optional<DeckResponse> getDeck(long deck_id) {
        DeckResponse deck = null;
        String sql = "SELECT deck_name, user_id FROM deck WHERE deck_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, deck_id);
        if (results.next()) {
            String deck_name = results.getString("deck_name");
            int user_id = results.getInt("user_id");
            sql = "SELECT card_id, card_quantity FROM card WHERE deck_id = ?";
            results = jdbcTemplate.queryForRowSet(sql, deck_id);
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

    public void deleteDeck(long deck_id) {
        this.deleteCardsFromDeck(deck_id);
        String sql2 = "DELETE FROM deck WHERE deck_id = ?";
        jdbcTemplate.update(sql2, deck_id);
    }

    public void updateDeck(DeckRequest request, long deck_id) {
        this.doUpdate(request, deck_id);
    }

    public List<Long> getDeckIdsByUserId(long userId) {
        List<Long> list = new ArrayList<>();
        String sql = "SELECT deck_id FROM deck WHERE user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        while (results.next()) {
            list.add(results.getLong("deck_id"));
        }
        return list;
    }

    private int insertDeck(DeckRequest request) {
        String deckName = request.getName();
        long userId = request.getUserId();
        String sql = "INSERT INTO deck(user_id, deck_name) VALUES (?, ?) RETURNING deck_id";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, deckName);
        return results.next() ? results.getInt("deck_id") : -1;
    }

    private void doUpdate(DeckRequest request, long deck_id) {
        this.updateDeckTable(request, deck_id);
        this.deleteCardsFromDeck(deck_id);
        this.addCardsToDeck(request, deck_id);
    }

    private void updateDeckTable(DeckRequest request, long deck_id) {
        String sql = "UPDATE deck SET deck_name = ? WHERE deck_id = ?";
        jdbcTemplate.update(sql, request.getName(), deck_id);
    }

    private void deleteCardsFromDeck(long deck_id) {
        String sql1 = "DELETE FROM card WHERE deck_id = ?";
        jdbcTemplate.update(sql1, deck_id);
    }

    private void addCardsToDeck(DeckRequest request, long deckId){
        String sql2 = "INSERT INTO card (deck_id, card_id, card_quantity) VALUES ";
        List<Card> cards = request.getCardList();
        String[] values = new String[cards.size()];
        for (int i = 0; i < values.length; i++) {
            Card c = cards.get(i);
            String cardId = c.getCard_id();
            int cardQuantity = c.getCard_quantity();
            values[i] =  "(" + deckId + ", '" + cardId + "', " + cardQuantity + ")";
        }
        String sql3 = String.join(", ", values);
        jdbcTemplate.update(sql2 + sql3);  // int[]columnTypes = {Types.INTEGER,Types.VARCHAR};
    }

}