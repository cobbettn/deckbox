package com.deckbop.api.controller.response;

import com.deckbop.api.controller.request.DeckRequest;
import com.deckbop.api.model.Card;

import java.util.List;

public class DeckResponse extends DeckRequest {
    private long id;
    public DeckResponse(String name, List<Card> cardList, long userId, long id) {
        super(name, cardList, userId);
        this.id = id;
    }
}
