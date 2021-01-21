package com.deckbop.app.controller.request;

import com.deckbop.app.controller.request.model.Credential;

import java.util.Map;

public class UserLoginRequest extends UserRegisterRequest {
    Boolean rememberMe;

    public UserLoginRequest(Map<String, String> credentials, String password, Boolean rememberMe) {
        super(credentials, password);
        this.rememberMe = rememberMe;
    }

    public Boolean getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(Boolean rememberMe) {
        this.rememberMe = rememberMe;
    }
}
