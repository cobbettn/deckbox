package com.deckbop.app.controller.request;

import java.util.Map;

public class UserUpdateRequest {
    String password;
    Map<String, String> credentials;

    public UserUpdateRequest(String password, Map<String, String> credentials) {
        this.password = password;
        this.credentials = credentials;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<String, String> getCredentials() {
        return credentials;
    }

    public void setCredentials(Map<String, String> credentials) {
        this.credentials = credentials;
    }
}
