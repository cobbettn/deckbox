package com.deckbop.app.controller.response;

import com.deckbop.app.controller.request.DeckPostRequest;
import com.deckbop.app.model.Card;

import java.util.List;

public class DeckGetResponse extends DeckPostRequest {
    private long id;

    public DeckGetResponse(String name, List<Card> cardList, long userId, long id) {
        super(name, cardList, userId);
        this.id = id;
    }
}
