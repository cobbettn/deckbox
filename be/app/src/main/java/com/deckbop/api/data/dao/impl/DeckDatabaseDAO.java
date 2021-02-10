package com.deckbop.api.data.dao.impl;

import com.deckbop.api.data.IDeckDatasource;
import com.deckbop.api.data.SQLTemplates;
import com.deckbop.api.data.dao.DatabaseDAO;
import com.deckbop.api.data.dao.impl.rowmapper.CardRowMapper;
import com.deckbop.api.data.dao.impl.rowmapper.DeckRowMapper;
import com.deckbop.api.model.Card;
import com.deckbop.api.model.Deck;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DeckDatabaseDAO extends DatabaseDAO implements IDeckDatasource {

    @Override
    public Deck getDeckById(long deck_id){
        return this.jdbcTemplate.queryForObject(SQLTemplates.getDeckById, new DeckRowMapper(), deck_id);
    }

    @Override
    public List<Card> getCardsByDeckId(long deck_id){
        return this.jdbcTemplate.queryForObject(SQLTemplates.getCardsByDeckId, new CardRowMapper(), deck_id);
    }

    @Override
    public void deleteDeck(long deck_id) {
        this.jdbcTemplate.update(SQLTemplates.deleteDeck, deck_id);
    }

    @Override
    public void deleteCardsFromDeck(long deck_id) {
        this.jdbcTemplate.update(SQLTemplates.deleteCardsFromDeck, deck_id);
    }

    @Override
    public List<Deck> getDecksByUserId(long user_id) {
        return this.jdbcTemplate.query(SQLTemplates.getDecksByUserId, new DeckRowMapper(), user_id);
    }

    @Override
    public Long insertDeck(String deckName, long userId) {
        return this.jdbcTemplate.queryForObject(SQLTemplates.insertDeck, Long.class, userId, deckName);
    }

    @Override
    public void updateDeckTable(String deckName, long deck_id) {
        this.jdbcTemplate.update(SQLTemplates.updateDeckTable, deckName, deck_id);
    }

    @Override
    public void addCardsToDeck(String sql){
        this.jdbcTemplate.update(sql);  // int[]columnTypes = {Types.INTEGER,Types.VARCHAR};
    }

    @Override
    public Long getNumDeckNameCollisions(long userId, String deckName, long deckId) {
        return this.jdbcTemplate.queryForObject(SQLTemplates.getNumDeckNameCollisions, Long.class, userId, deckName, deckId);
    }

}