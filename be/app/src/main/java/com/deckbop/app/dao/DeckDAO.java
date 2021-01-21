package com.deckbop.app.dao;

import com.deckbop.app.controller.request.UpdateDeckRequest;
import com.deckbop.app.controller.response.DeckGetResponse;
import com.deckbop.app.controller.request.DeckPostRequest;
import com.deckbop.app.model.Card;
import com.deckbop.app.service.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    
    public void createDeck(DeckPostRequest request){
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            String deckName = request.getName();
            long userId = request.getUserId();
            String sql = "INSERT INTO deck(user_id, deck_name) VALUES (?, ?) RETURNING deck_id";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, deckName);
            results.next();
            int deckId = results.getInt("deck_id");
            addCardsToDeck(request, deckId);
        } catch (Exception e){
            loggingService.error("SQL error while creating a deck");
        }
    }
    public Optional<DeckGetResponse> getDeck(long deck_id) {
        DeckGetResponse deck = null;
        try {
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
                deck = new DeckGetResponse(deck_name, cardList, user_id, deck_id);
            }
        }
        catch (Exception e) {
            loggingService.error("SQL error while retrieving deck: deckId = " + deck_id);
        }

        return Optional.ofNullable(deck);
    }

    public void deleteDeck(long id){
        try {
            String sql1 = "DELETE FROM card WHERE deck_id = ?";
            jdbcTemplate.update(sql1, id);
            String sql2 = "DELETE FROM deck WHERE deck_id = ?";
            jdbcTemplate.update(sql2, id);
        } catch (DataAccessException e) {
            loggingService.error("SQL error while deleting deck: deckId = " + id);
        }
    }

    public Optional<List<Long>> getDeckIdsByUserId(long userId) {
        List<Long> list = null;
        try {
            list = new ArrayList<Long>();
            String sql = "SELECT deck_id FROM deck WHERE user_id = ?";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            while (results.next()) {
                list.add(results.getLong("deck_id"));
            }
        } catch (DataAccessException e) {
            loggingService.error("SQL error in deleteDeck()");
        }
        return Optional.ofNullable(list);
    }

    public void deleteUserDecks(long userId) {
        Optional<List<Long>> list = getDeckIdsByUserId(userId);
        list.ifPresent(longs -> longs.forEach(this::deleteDeck));
    }

    public ResponseEntity<?> updateDeck(DeckPostRequest request, long id) {
        try {
            String sql = "UPDATE deck SET deck_name = ? WHERE deck_id = ?";
            jdbcTemplate.update(sql, request.getName(), id);
            sql = "DELETE FROM card WHERE deck_id = ?";
            jdbcTemplate.update(sql, id);
            addCardsToDeck(request, id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (DataAccessException e) {
            loggingService.error("SQL Error while updating deck");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public void addCardsToDeck(DeckPostRequest request, long deckId){
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
//          int[]columnTypes = {Types.INTEGER,Types.VARCHAR};
        jdbcTemplate.update(sql2 + sql3);
    }

}