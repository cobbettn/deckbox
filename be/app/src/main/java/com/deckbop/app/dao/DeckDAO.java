package com.deckbop.app.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.dao.DataAccessException;

import org.springframework.security.core.context.SecurityContextHolder;

import com.deckbop.app.model.Deck;
import com.deckbop.app.controller.dto.DeckDTO;

@Component
public class DeckDAO {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public void createDeck(DeckDTO deckDto){
        Deck deck = new Deck();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try{
            String deckName = deckDto.getName();
            String sql = "INSERT INTO decks (user_id, decklist_id, deck_name) VALUES (?, ?, ?)";
            jdbcTemplate.update(sql, userId, deckListId, deckName);
        }catch(DataAccessException e){
            
        }
        
    }
        

}