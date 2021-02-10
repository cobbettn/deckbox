package com.deckbop.api.controller.request;

import com.deckbop.api.model.Card;
import com.deckbop.api.model.Deck;

import java.util.List;

public class DeckRequest extends Deck {
    public DeckRequest(long deckId, long userId, String deckName, List<Card> cardList) {
        super(deckId, userId, deckName, cardList);
    }
}
