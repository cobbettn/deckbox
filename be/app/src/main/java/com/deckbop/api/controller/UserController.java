package com.deckbop.api.controller;

import com.deckbop.api.controller.request.UserLoginRequest;
import com.deckbop.api.controller.request.UserRegisterRequest;
import com.deckbop.api.controller.request.UserUpdateRequest;
import com.deckbop.api.controller.response.UserLoginResponse;
import com.deckbop.api.exception.UserRegisterException;
import com.deckbop.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterRequest request) {
        ResponseEntity<?> response;
        try {
            response = userService.registerUser(request);
        }
        catch (DataAccessException | UserRegisterException e) {
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest request) {
        ResponseEntity<?> response = null;
        try {
            UserLoginResponse userLoginResponse = userService.loginUser(request);
            if (Optional.ofNullable(userLoginResponse).isPresent()) {
                HttpHeaders httpHeaders = userService.getJWTHeaders(userLoginResponse.getJwtToken());
                response = new ResponseEntity<>(userLoginResponse, httpHeaders, HttpStatus.OK);
            }
        }
        catch (Exception e) {
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody UserUpdateRequest request) {
        ResponseEntity<?> response;
        try {
            userService.updateUser(id, request);
            response = new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e) {
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        ResponseEntity<?> response;
        try {
            userService.deleteUser(id);
            response = new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e) {
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return response;
    }

}
