package com.deckbop.api.model;


public class Deck {
    
    private int deckId;
    private int userId;
    private int deckListId;
    String deckName;
    
    public Deck(int deckId, int userId, int deckListId, String deckName){
        this.deckId = deckId;
        this.userId = userId;
        this.deckListId = deckListId;
        this.deckName = deckName;
    }
    
    public int getDeckId() {
        return deckId;
    }

    public void setDeckId(int deckId) {
        this.deckId = deckId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDeckListId() {
        return deckListId;
    }

    public void setDeckListId(int deckListId) {
        this.deckListId = deckListId;
    }

    public String getDeckName() {
        return deckName;
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }
    

}
