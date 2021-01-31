package com.deckbop.api.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserLoginSuccessResponse {

    String jwtToken;
    long userId;

    public UserLoginSuccessResponse(String jwtToken, long userId) {
        this.jwtToken = jwtToken;
        this.userId = userId;
    }

    @JsonProperty("token")
    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
