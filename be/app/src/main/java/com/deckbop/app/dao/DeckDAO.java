package com.deckbop.app.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import java.util.List;
import org.springframework.security.core.context.SecurityContextHolder;
import java.sql.Types;

import com.deckbop.app.model.Deck;
import com.deckbop.app.model.Card;
import com.deckbop.app.controller.dto.DeckDTO;

@Component
public class DeckDAO {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public void createDeck(DeckDTO deckDto){
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try{
            String deckName = deckDto.getName();
            long userId = deckDto.getUserId();
            String sql = "INSERT INTO deck(user_id, deck_name) VALUES (?, ?) RETURNING deck_id";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, deckName);
            results.next();
            int deckId = results.getInt("deck_id");
            String sql2 = "INSERT INTO card(deck_id, card_id, card_quantity) VALUES ";
            List<Card>cards = deckDto.getCardList();
            String[] values = new String[cards.size()];
            for(int i =0; i < values.length; i++){
                Card c = cards.get(i);
                String cardId = c.getCard_id();
                int cardQuantity = c.getCard_quantity();
                values[i] =  "(" + deckId + ", '" + cardId + "', " + cardQuantity + ")";
            }
            String sql3 = String.join(", ", values);
//          int[]columnTypes = {Types.INTEGER,Types.VARCHAR};
            System.out.println(sql2 + sql3);
            jdbcTemplate.update(sql2 + sql3);
            
            
        }catch(DataAccessException e){
            
        }

        
    }    

}