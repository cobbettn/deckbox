package com.deckbop.app.service.impl;


import com.deckbop.app.controller.request.UserLoginRequest;
import com.deckbop.app.controller.response.UserLoginResponse;
import com.deckbop.app.exception.EmailNotRegisteredException;
import com.deckbop.app.exception.NoUsernameOrEmailException;
import com.deckbop.app.exception.UserLoginException;
import com.deckbop.app.model.User;
import com.deckbop.app.security.jwt.JWTFilter;
import com.deckbop.app.security.jwt.JWTProvider;
import com.deckbop.app.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;


@Component
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private LoggingServiceImpl loggingService;

    @Autowired
    UserServiceImpl userService;

    private final JWTProvider JWTProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public AuthenticationServiceImpl(JWTProvider JWTProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.JWTProvider = JWTProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @Override
    public ResponseEntity<?> authorizeAndReturnJWTToken(UserLoginRequest request) throws AuthenticationException, UserLoginException {
        String username = this.getUsernameFromCredentials(request.getCredentials());
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, request.getPassword());
        Authentication authentication = null;
        try {
            authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            loggingService.info(username + " logged in");
        }
        catch (BadCredentialsException e) {
            loggingService.warn(username + " provided bad credentials");
            throw e;
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // boolean rememberMe = request.isRememberMe() != null && request.isRememberMe();
        String jwt = JWTProvider.createToken(authentication, true); // HACK: hard-coded true until request is done
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(new UserLoginResponse(jwt), httpHeaders, HttpStatus.OK);
    }

    @Override
    public String getUsernameFromCredentials(Map<String, String> credentials) throws EmailNotRegisteredException, NoUsernameOrEmailException {
        String username = credentials.get("username");
        if (username == null) {
            String email = credentials.get("email");
            if (email != null) {
                Optional<User> user = userService.getUserByEmail(email);
                if (user.isPresent()) {
                    username = user.get().getUsername();
                } else {
                    throw new EmailNotRegisteredException("Email is not registered");
                }
            } else {
                throw new NoUsernameOrEmailException("no username or email provided");
            }
        }
        return username;
    }
}