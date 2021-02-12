package com.deckbop.api.model;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Deck {
    
    private long id;
    private long userId;
    String name;
    List<Card> cards;
    ArrayList<LinkedHashMap> scryFallCards;
    
    public Deck(long id, long userId, String deckName, List<Card> cards){
        this.id = id;
        this.userId = userId;
        this.name = deckName;
        this.cards = cards;
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

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<LinkedHashMap> getScryFallCards() {
        return scryFallCards;
    }

    public void setScryFallCards(ArrayList<LinkedHashMap> scryFallCards) {
        this.scryFallCards = scryFallCards;
    }
}
