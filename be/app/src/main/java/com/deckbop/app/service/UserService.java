package com.deckbop.app.service;

import com.deckbop.app.controller.request.UserLoginRequest;
import com.deckbop.app.controller.request.UserRegisterRequest;
import com.deckbop.app.controller.request.UserUpdateRequest;
import com.deckbop.app.exception.*;
import com.deckbop.app.model.User;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface UserService {
    public Optional<User> getUserByLogin(String username);
    public Optional<User> getUserByEmail(String email);
    public ResponseEntity<?> registerUser(UserRegisterRequest request) throws CredentialsInUseException;
    public ResponseEntity<?> loginUser(UserLoginRequest request) throws UserLoginException;
    public void deleteUser(long user_id);
    public void updateUser(long id, UserUpdateRequest request);
}
