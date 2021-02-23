package com.deckbop.api.data.dao.rowmapper;

import com.deckbop.api.model.Card;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CardRowMapper implements RowMapper<List<Card>> {

    @Override
    public List<Card> mapRow(ResultSet resultSet, int i) throws SQLException {
        List<Card> cardList = new ArrayList<>();
        do {
            String cardId = resultSet.getString("card_id");
            int cardQty = resultSet.getInt("card_quantity");
            Card card = new Card(cardId, cardQty);
            cardList.add(card);
        }
        while (resultSet.next());
        return cardList;
    }
}
