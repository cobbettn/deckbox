package com.deckbop.api.controller.request;

import java.util.Map;

public class UserRegisterRequest {
    Map<String, String> credentials;
    private String password;

    public UserRegisterRequest(Map<String, String> credentials, String password) {
        this.credentials = credentials;
        this.password = password;
    }

    public Map<String, String> getCredentials() {
        return credentials;
    }

    public void setCredentials(Map<String, String> credentials) {
        this.credentials = credentials;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
