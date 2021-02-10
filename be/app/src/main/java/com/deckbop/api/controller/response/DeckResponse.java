package com.deckbop.api.controller.response;

import com.deckbop.api.model.Card;
import com.deckbop.api.model.Deck;

import java.util.List;

public class DeckResponse extends Deck {
    public DeckResponse(long deckId, long userId, String deckName, List<Card> cardList) {
        super(deckId, userId, deckName, cardList);
    }
}
