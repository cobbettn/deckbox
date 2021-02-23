package com.deckbop.api.data.dao.rowmapper;

import com.deckbop.api.model.Deck;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DeckRowMapper implements RowMapper<Deck> {

    @Override
    public Deck mapRow(ResultSet resultSet, int i) throws SQLException {
        long deckId = resultSet.getLong("deck_id");
        long userId = resultSet.getLong("user_id");
        String name = resultSet.getString("deck_name");
        return new Deck(deckId, userId, name);
    }

}

