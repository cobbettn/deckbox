package com.deckbop.app.controller;

import com.deckbop.app.controller.request.UserLoginRequest;
import com.deckbop.app.controller.request.UserRegisterRequest;
import com.deckbop.app.controller.request.UserUpdateRequest;
import com.deckbop.app.dao.UserDAO;
import com.deckbop.app.exception.UserLoginException;
import com.deckbop.app.security.service.AuthenticationService;
import com.deckbop.app.service.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    UserDAO userDAO;
    @Autowired
    LoggingService loggingService;
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/user/register")
    public ResponseEntity<String> register(@RequestBody UserRegisterRequest request) {
        if (request.getCredentials().containsKey("username") && request.getCredentials().containsKey("email")) {
            try {
                userDAO.registerUser(request.getCredentials().get("username"), request.getCredentials().get("email"), request.getPassword());
            }
            catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>("registration successful", HttpStatus.CREATED);
    }

    @PostMapping("/user/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest request) {
        try {
            return authenticationService.authorize(request);
        }
        catch (UserLoginException | AuthenticationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        userDAO.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/user/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody UserUpdateRequest request){
        userDAO.updateUser(id, request);
        return null;
    }
}
