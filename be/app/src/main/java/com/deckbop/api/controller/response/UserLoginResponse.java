package com.deckbop.api.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserLoginResponse {

    String jwtToken;

    public UserLoginResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    @JsonProperty("token")
    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

}
