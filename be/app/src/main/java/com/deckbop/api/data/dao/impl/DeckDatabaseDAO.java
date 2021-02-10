package com.deckbop.api.data.dao.impl;

import com.deckbop.api.data.SQLTemplates;
import com.deckbop.api.data.dao.DatabaseDAO;
import com.deckbop.api.data.IDeckDatasource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class DeckDatabaseDAO extends DatabaseDAO implements IDeckDatasource {

    @Override
    public SqlRowSet getDeckById(long deck_id){
        return this.jdbcTemplate.queryForRowSet(SQLTemplates.getDeckById, deck_id);
    }

    @Override
    public SqlRowSet getCardsByDeckId(long deck_id){
        return this.jdbcTemplate.queryForRowSet(SQLTemplates.getCardsByDeckId, deck_id);
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
    public SqlRowSet getDeckIdsByUserId(long userId) {
        return this.jdbcTemplate.queryForRowSet(SQLTemplates.getDeckIdsByUserId, userId);
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
    public SqlRowSet checkDeckInDeckTable(long userId, String deckName){
        return this.jdbcTemplate.queryForRowSet(SQLTemplates.checkDeckInDeckTable, userId, deckName);
    }

}