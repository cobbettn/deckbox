package com.deckbop.api.controller.response;

public class CreateDeckResponse {
    long deckId;

    public CreateDeckResponse(long deckId) {
        this.deckId = deckId;
    }

    public long getDeckId() {
        return deckId;
    }

    public void setDeckId(long deckId) {
        this.deckId = deckId;
    }
}
