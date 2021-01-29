package com.deckbop.api.controller.request;

import java.util.HashMap;

public class UserActivationRequest {

    String activation_token;

    public String getActivation_token() {
        return activation_token;
    }

    public void setActivation_token(String activation_token) {
        this.activation_token = activation_token;
    }
}

