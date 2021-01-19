package com.deckbop.app.controller.dto;

import com.deckbop.app.model.Card;
import java.util.List;

public class DeckDTO {
    
    private String name;
    private List<Card>cardList;

    public DeckDTO(String name, List<Card> cardList) {
        this.name = name;
        this.cardList = cardList;
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
