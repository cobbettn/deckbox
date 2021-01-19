package com.deckbop.app.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import com.deckbop.app.controller.dto.DeckDTO;
import com.deckbop.app.dao.DeckDAO;


@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/deck")
public class CreateDeckController {
    
    @Autowired
    DeckDAO deckDAO;
    
    @RequestMapping( value = "", method = RequestMethod.POST)
    public void createDeck(@RequestBody DeckDTO deckDto){
        deckDAO.createDeck(deckDto);
        
    }
//    @RequestMapping( value = "/{id}", method = RequestMethod.GET)
//    public Deck getDeck(@PathVariable long id){
//        
//    }

}
