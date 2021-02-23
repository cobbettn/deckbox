package com.deckbop.api.service.scryfall;

import com.deckbop.api.model.Card;
import com.deckbop.api.model.scryfall.Identifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class ScryfallService {

    private final String SCRYFALL_API_URL = "https://api.scryfall.com";
    private final String COLLECTIONS_ENDPOINT = "/cards/collection";
    private final int MAX_IDENTIFIERS = 75; // maximum number of identifiers accepted by scryfall
    private final long SLEEP = 50L; // recommended pause between requests to scryfall API

    private final RestTemplate restTemplate = new RestTemplate();

    public ArrayList<LinkedHashMap> fetchCardList(List<Card> cards)  {
        int size = cards.size();
        ArrayList<LinkedHashMap> cardList = new  ArrayList<>();
        int numRequests = getNumberOfRequests(size);
        for (int i = 0; i < numRequests; i++) {
            List<Identifier> identifiers = new ArrayList<>();
            int start = i * MAX_IDENTIFIERS;
            int end = start + MAX_IDENTIFIERS;
            List<Card> subList = cards.subList(start, Math.min(end, size));
            subList.forEach(card -> identifiers.add(new Identifier(card.getCard_id())));
            try {
                LinkedHashMap<String, ArrayList> response = this.restTemplate.postForObject(
                        SCRYFALL_API_URL + COLLECTIONS_ENDPOINT,
                        this.makeHttpEntity(identifiers),
                        LinkedHashMap.class
                );
                cardList.addAll(response.get("data"));
                Thread.sleep(SLEEP);
            }
            catch (InterruptedException e) {
                System.out.println("Thread error");
            }
            catch (RestClientException e) {
                System.out.println("Scryfall error");
            }
        }
        return cardList;
    }

    private int getNumberOfRequests(int size) {
        int numRequests;
        int num = size / MAX_IDENTIFIERS;
        int mod = size % MAX_IDENTIFIERS;
        if (num <= MAX_IDENTIFIERS) numRequests = 1;
        else numRequests = mod == 0 ? num : num + 1;
        return numRequests;
    }

    private HttpEntity<ScryfallCardsCollectionRequest> makeHttpEntity(List<Identifier> identifiers) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(new ScryfallCardsCollectionRequest(identifiers), headers);
    }
}

