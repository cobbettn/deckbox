package com.deckbop.api.controller.response;

import com.deckbop.api.model.Deck;

import java.util.List;

public class GetUserDecksResponse {
    List<Deck> deckList;

    public GetUserDecksResponse(List<Deck> deckList) {
        this.deckList = deckList;
    }

    public List<Deck> getDeckList() {
        return deckList;
    }

    public void setDeckList(List<Deck> deckList) {
        this.deckList = deckList;
    }
}
