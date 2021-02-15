package com.deckbop.api.controller;

import com.deckbop.api.controller.response.ErrorMessageResponse;
import com.deckbop.api.exception.DeckNameExistsException;
import com.deckbop.api.model.Deck;
import com.deckbop.api.service.DeckService;
import com.deckbop.api.service.ScryfallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping("/deck")
public class DeckController {
    @Autowired
    DeckService deckService;

    @Autowired
    ScryfallService scryfallService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> createDeck(@RequestBody Deck request){
        try {
            deckService.createDeck(request);
        }
        catch (DeckNameExistsException e){
            return new ResponseEntity<>(new ErrorMessageResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> updateDeck(@PathVariable long id, @RequestBody Deck request){
        try {
            Deck response = deckService.updateDeck(request, id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (DeckNameExistsException e){
            return new ResponseEntity<>(new ErrorMessageResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getDeck(@PathVariable long id) {
        try {
            Deck response = deckService.getDeck(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteDeck(@PathVariable long id) {
        try {
            deckService.deleteDeck(id);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
