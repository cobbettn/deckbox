package com.deckbop.api.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserLoginSuccessResponse {

    String jwtToken;
    long userId;
    String userName;
    String email;
    String password;

    public UserLoginSuccessResponse(String jwtToken, long userId, String userName, String email, String password) {
        this.jwtToken = jwtToken;
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.password = password;
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

    public String getUserName() { return userName; }

    public void setUserName(String userName) { this.userName = userName; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }
}
