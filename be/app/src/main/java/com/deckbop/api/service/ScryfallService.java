package com.deckbop.api.service;

import com.deckbop.api.controller.request.ScryfallCardsCollectionRequest;
import com.deckbop.api.model.Card;
import com.deckbop.api.model.scryfall.Identifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class ScryfallService {

    private final String SCRYFALL_API_URL = "https://api.scryfall.com";
    private final int MAX_CHUNK_SIZE = 75;
    public RestTemplate restTemplate = new RestTemplate();

    public LinkedHashMap<String, ArrayList> getCardChunk(List<Card> cardList){
        List<Identifier> identifiers = new ArrayList<>();
        cardList.forEach(card -> {
            identifiers.add(new Identifier(card.getCard_id()));
        });
        return restTemplate.postForObject(SCRYFALL_API_URL + "/cards/collection", this.makeEntity(identifiers), LinkedHashMap.class);
    }

    private HttpEntity<ScryfallCardsCollectionRequest> makeEntity(List<Identifier> identifiers) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(new ScryfallCardsCollectionRequest(identifiers), headers);
    }
}

