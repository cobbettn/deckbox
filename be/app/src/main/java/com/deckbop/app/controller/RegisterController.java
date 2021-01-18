package com.deckbop.app.controller;

import com.deckbop.app.Exception.RegisterException;
import com.deckbop.app.controller.dto.RegisterDto;
import com.deckbop.app.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {
    @Autowired
    UserDAO userDAO;
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterDto registerDto) {
        try {
            userDAO.createUser(registerDto);
            return new ResponseEntity<>(HttpStatus.CREATED); // 201
        }
        catch (RegisterException ex) {
            return new ResponseEntity<>(HttpStatus.CONFLICT); // 409
        }
    }
}
