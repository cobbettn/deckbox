package com.deckbop.api.service;


import com.deckbop.api.controller.request.UserLoginRequest;
import com.deckbop.api.exception.UserLoginException;
import com.deckbop.api.model.User;
import com.deckbop.api.security.jwt.JWTProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
     LoggingService loggingService;

    @Autowired
    UserService userService;

    private final JWTProvider JWTProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public AuthenticationService(JWTProvider JWTProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.JWTProvider = JWTProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    public String authorizeAndGetJWTToken(UserLoginRequest request) throws AuthenticationException, UserLoginException {
        Optional<String> username = getUsernameFromCredentials(request.getCredentials());
        if (username.isEmpty()) {
            throw new UserLoginException("Bad credentials");
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username.get(), request.getPassword());
        Authentication authentication = null;
        try {
            authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            loggingService.info(this, username.get() + " logged in");
        }
        catch (BadCredentialsException e) {
            loggingService.warn(this, username.get() + " provided bad credentials");
            throw e;
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // boolean rememberMe = request.isRememberMe() != null && request.isRememberMe();
        return JWTProvider.createToken(authentication, true);
    }

    public Optional<String> getUsernameFromCredentials(Map<String, String> credentials) throws UserLoginException {
        Optional<String> username = Optional.ofNullable(credentials.get("username"));
        if (username.isEmpty()) {
            Optional<String> email = Optional.ofNullable(credentials.get("email"));
            if (email.isPresent()) {
                Optional<User> user = userService.getUserByEmail(email.get());
                if (user.isPresent()) {
                    username = Optional.ofNullable(user.get().getUsername());
                }
                else {
                    throw new UserLoginException("Invalid email");
                }
            }
            else {
                throw new UserLoginException("No credentials provided");
            }
        }
        return username;
    }

}