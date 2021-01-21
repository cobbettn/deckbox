package com.deckbop.app.controller.request;

import com.deckbop.app.model.Card;

import java.util.List;

public class UpdateDeckRequest extends DeckPostRequest{
    private long deckId;
    public UpdateDeckRequest(String name, List<Card> cardList, long userId, long deckId) {
        super(name, cardList, userId);
        this.deckId = deckId;
    }

    public long getDeckId() {
        return deckId;
    }

    public void setDeckId(long deckId) {
        this.deckId = deckId;
    }
}
