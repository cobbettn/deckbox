package com.deckbop.api.controller;

import com.deckbop.api.controller.request.UserActivationRequest;
import com.deckbop.api.controller.request.UserLoginRequest;
import com.deckbop.api.controller.request.UserRegisterRequest;
import com.deckbop.api.controller.request.UserUpdateRequest;
import com.deckbop.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterRequest request) {
        return userService.registerUser(request);
    }

    @PostMapping("/activate")
    public ResponseEntity<?> activate(@RequestBody UserActivationRequest request){
        ResponseEntity<?> response = new ResponseEntity<>(HttpStatus.OK);
        try {
            userService.activateUser(request);
        } catch (Exception e) {
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest request) {
        return userService.loginUser(request);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody UserUpdateRequest request) {
        ResponseEntity<?> response =new ResponseEntity<>(HttpStatus.OK);
        try {
            userService.updateUser(id, request);
        }
        catch (Exception e) {
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        ResponseEntity<?> response = new ResponseEntity<>(HttpStatus.OK);
        try {
            userService.deleteUser(id);
        }
        catch (Exception e) {
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return response;
    }

}
