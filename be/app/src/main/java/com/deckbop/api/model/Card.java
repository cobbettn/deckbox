
package com.deckbop.api.model;


public class Card {
    
    private String card_id;
    private int card_quantity;

    public Card(String card_id, int card_quantity) {
        this.card_id = card_id;
        this.card_quantity = card_quantity;
    }

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

    public int getCard_quantity() {
        return card_quantity;
    }

    public void setCard_quantity(int card_quantity) {
        this.card_quantity = card_quantity;
    }
}
