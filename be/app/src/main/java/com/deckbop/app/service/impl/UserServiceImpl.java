package com.deckbop.app.service.impl;

import com.deckbop.app.controller.request.UserLoginRequest;
import com.deckbop.app.controller.request.UserRegisterRequest;
import com.deckbop.app.controller.request.UserUpdateRequest;
import com.deckbop.app.dao.UserDAO;
import com.deckbop.app.exception.CredentialsInUseException;
import com.deckbop.app.exception.UserLoginException;
import com.deckbop.app.model.User;
import com.deckbop.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    UserDAO userDAO;

    @Autowired
    LoggingServiceImpl loggingService;

    @Autowired
    AuthenticationServiceImpl authenticationServiceImpl;

    @Override
    public Optional<User> getUserByLogin(String username) {
        User user = null;
        try {
            Optional<User> u =  userDAO.getUserByLogin(username);
            if (u.isPresent()) {
                user = u.get();
            }
        } catch (DataAccessException e) {
            loggingService.error("SQL error while getting user: username = " + username);
        }
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> getUserByEmail(String email) throws DataAccessException {
        User user = null;
        try {
            Optional<User> u = userDAO.getUserByEmail(email);
            if (u.isPresent()) {
                user = u.get();
            }
        } catch (DataAccessException e) {
            loggingService.error("SQL error while getting user: email = " + email);
        }
        return Optional.ofNullable(user);
    }

    @Override
    public ResponseEntity<?> registerUser(UserRegisterRequest request) throws DataAccessException, CredentialsInUseException {
        String email = request.getCredentials().get("email");
        String username  = request.getCredentials().get("username");
        if (email != null && username != null) {
            Optional<User> badEmail = userDAO.getUserByEmail(email);
            Optional<User> badUsername = userDAO.getUserByLogin(username);
            if (badEmail.isEmpty() && badUsername.isEmpty()) {
                try {
                    userDAO.registerUser(username, email, request.getPassword());
                    return new ResponseEntity<>(HttpStatus.CREATED); // user registered, return success response
                }
                catch (DataAccessException e) {
                    loggingService.error("SQL error while registering a user");
                    throw e;
                }
            }
            else {
                CredentialsInUseException e;
                if (badEmail.isPresent() && badUsername.isPresent()) {
                    e = new CredentialsInUseException("username and email already taken");
                }
                else if (badEmail.isPresent()) {
                    e = new CredentialsInUseException("email already registered");
                }
                else {
                    e = new CredentialsInUseException("username already registered");
                }
                loggingService.error("Error while registering user");
                throw e;
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Override
    public void deleteUser(long user_id) {
        try {
            userDAO.deleteUser(user_id);
        }
        catch (DataAccessException e) {
            loggingService.error("SQL error while deleting user");
            throw e;
        }
    }

    @Override
    public void updateUser(long id, UserUpdateRequest request) {
        try {
            userDAO.updateUser(id, request);
        }
        catch (DataAccessException e) {
            loggingService.error("SQL Error while updating user");
            throw e;
        }
    }

    @Override
    public ResponseEntity<?> loginUser(UserLoginRequest request) throws UserLoginException {
        try {
            return authenticationServiceImpl.authorizeAndReturnJWTToken(request);
        }
        catch (AuthenticationException | UserLoginException e) {
            loggingService.error("Error while logging in user");
            throw e;
        }
    }
}
