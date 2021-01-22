package com.deckbop.app.controller;

import com.deckbop.app.controller.request.UserLoginRequest;
import com.deckbop.app.controller.request.UserRegisterRequest;
import com.deckbop.app.controller.request.UserUpdateRequest;
import com.deckbop.app.exception.UserLoginException;
import com.deckbop.app.exception.UserRegisterException;
import com.deckbop.app.service.impl.AuthenticationServiceImpl;
import com.deckbop.app.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    AuthenticationServiceImpl authenticationServiceImpl;

    @PostMapping("/user/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterRequest request) {
        try {
            return userService.registerUser(request); // 201 when created
        }
        catch (DataAccessException | UserRegisterException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/user/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest request) {
        try {
            return authenticationServiceImpl.authorizeAndReturnJWTToken(request); // 200 when login
        }
        catch (AuthenticationException | UserLoginException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/user/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody UserUpdateRequest request) {
        try {
            userService.updateUser(id, request);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        try {
            userService.deleteUser(id);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
