package com.deckbop.app.controller.request;

import com.deckbop.app.model.Card;
import java.util.List;

public class DeckRequest {
    
    private String name;
    private List<Card>cardList;
    private long userId;

    public DeckRequest(String name, List<Card> cardList, long userId) {
        this.name = name;
        this.cardList = cardList;
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Card> getCardList() {
        return cardList;
    }

    public void setCardList(List<Card> cardList) {
        this.cardList = cardList;
    }
}
