package com.deckbop.app.service;

import com.deckbop.app.controller.request.UserLoginRequest;
import com.deckbop.app.exception.UserLoginException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;

import java.util.Map;

public interface AuthenticationService {
    public ResponseEntity<?> authorizeAndReturnJWTToken(UserLoginRequest request) throws AuthenticationException, UserLoginException;
    public String getUsernameFromCredentials(Map<String, String> credentials) throws UserLoginException;
}
