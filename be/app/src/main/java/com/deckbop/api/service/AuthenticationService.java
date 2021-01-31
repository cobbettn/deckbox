package com.deckbop.api.service;


import com.deckbop.api.security.jwt.JWTProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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

    public String authenticateAndGetJWTToken(String username, String password) throws AuthenticationException {
        String jwt = null;
        Authentication authentication;
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        try {
            authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            loggingService.info(this, username + " logged in");
        }
        catch (BadCredentialsException e) {
            loggingService.info(this, username + " provided bad credentials");
            throw e;
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        jwt = JWTProvider.createToken(authentication, true);
        // boolean rememberMe = request.isRememberMe() != null && request.isRememberMe();
        return jwt;
    }

}