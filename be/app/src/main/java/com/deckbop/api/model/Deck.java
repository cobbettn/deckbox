package com.deckbop.api.model;


import java.util.List;

public class Deck {
    
    private long id;
    private long userId;
    String name;
    List<Card> cardList;
    
    public Deck(long id, long userId, String deckName, List<Card> cardList){
        this.id = id;
        this.userId = userId;
        this.name = deckName;
        this.cardList = cardList;
    }

    public Deck(long id, long userId, String deckName){
        this.id = id;
        this.userId = userId;
        this.name = deckName;
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public List<Card> getCardList() {
        return cardList;
    }

    public void setCardList(List<Card> cardList) {
        this.cardList = cardList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
