package com.deckbop.api.controller.request;

import com.deckbop.api.model.scryfall.Identifier;

import java.util.List;

public class ScryfallCardsCollectionRequest {

    List<Identifier> identifiers;

    public ScryfallCardsCollectionRequest(List<Identifier> identifiers) {
        this.identifiers = identifiers;
    }

    public List<Identifier> getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(List<Identifier> identifiers) {
        this.identifiers = identifiers;
    }
}
