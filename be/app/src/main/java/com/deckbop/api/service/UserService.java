package com.deckbop.api.service;

import com.deckbop.api.controller.request.UserLoginRequest;
import com.deckbop.api.controller.request.UserRegisterRequest;
import com.deckbop.api.controller.request.UserUpdateRequest;
import com.deckbop.api.controller.response.UserLoginResponse;
import com.deckbop.api.data.IUserDatasource;
import com.deckbop.api.data.dao.impl.UserDatasourceDAO;
import com.deckbop.api.exception.CredentialsInUseException;
import com.deckbop.api.exception.UserLoginException;
import com.deckbop.api.model.User;
import com.deckbop.api.security.jwt.JWTFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    IUserDatasource userDatasource = new UserDatasourceDAO();

    @Autowired
    LoggingService loggingService;

    @Autowired
    DeckService deckService;

    @Bean
    PasswordEncoder getPasswordEncoder() {return new BCryptPasswordEncoder();}

    public Optional<User> getUserByLogin(String username) {
        User user = null;
        try {
            SqlRowSet results = userDatasource.getUserByLogin(username);
            if (results.next()) {
                user = getUserFromResults(results);
            }
        } catch (DataAccessException e) {
            loggingService.error(UserService.class,"SQL error while getting user: username = " + username);
            throw e;
        }
        return Optional.ofNullable(user);
    }

    public Optional<User> getUserByEmail(String email) {
        User user = null;
        try {
            SqlRowSet results = userDatasource.getUserByEmail(email);
            if (results.next()) {
                user = getUserFromResults(results);
            }
        } catch (DataAccessException e) {
            loggingService.error(UserService.class,"SQL error while getting user: email = " + email);
            throw e;
        }
        return Optional.ofNullable(user);
    }

    private User getUserFromResults(SqlRowSet results) {
        return new User(
                results.getLong("user_id"),
                results.getString("username"),
                results.getString("pw"),
                results.getString("email"),
                results.getString("account_role"),
                results.getBoolean("is_activated")
        );
    }

    public ResponseEntity<?> registerUser(UserRegisterRequest request) throws DataAccessException, CredentialsInUseException {
        Optional<String> email = Optional.ofNullable(request.getCredentials().get("email"));
        Optional<String> username  = Optional.ofNullable(request.getCredentials().get("username"));
        if (email.isPresent() && username.isPresent()) {
            SqlRowSet emailResults = userDatasource.getUserByEmail(email.get());
            SqlRowSet usernameResults = userDatasource.getUserByLogin(username.get());
            boolean emailInUse = emailResults.next();
            boolean usernameInUse = usernameResults.next();
            if (!emailInUse && !usernameInUse) {
                try {
                    userDatasource.registerUser(username.get(), email.get(), getPasswordEncoder().encode(request.getPassword()));
                    return new ResponseEntity<>(HttpStatus.CREATED);
                }
                catch (DataAccessException e) {
                    loggingService.error(UserService.class,"SQL error while registering a user");
                    throw e;
                }
            }
            else {
                loggingService.error(UserService.class,"Error while registering user");
                if (emailInUse && usernameInUse) {
                    throw new CredentialsInUseException("username and email already taken");
                }
                else if (emailInUse) {
                    throw new CredentialsInUseException("email already registered");
                }
                else {
                    throw new CredentialsInUseException("username already registered");
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public void deleteUser(long user_id) {
        try {
            deckService.deleteUserDecks(user_id);
            userDatasource.deleteUser(user_id);
        }
        catch (DataAccessException e) {
            loggingService.error(UserService.class,"SQL error while deleting user");
            throw e;
        }
    }

    public void updateUser(long user_id, UserUpdateRequest request) {
        boolean updated = false;
        try {
            Optional<String> username = Optional.ofNullable(request.getCredentials().get("username"));
            Optional<String> email = Optional.ofNullable(request.getCredentials().get("email"));
            Optional<String> password = Optional.ofNullable(request.getPassword());
            if (username.isPresent() && email.isPresent() && password.isPresent()) {
                userDatasource.updateUser(user_id, username.get(), getPasswordEncoder().encode(password.get()), email.get());
            }
        }
        catch (DataAccessException e) {
            loggingService.error(UserService.class,"SQL Error while updating user");
            throw e;
        }
    }

    public Optional<UserLoginResponse> loginUser(UserLoginRequest request) throws UserLoginException {
        UserLoginResponse response = null;
        try {
            Optional<String> jwt = Optional.ofNullable(authenticationService.authorizeAndGetJWTToken(request));
            if (jwt.isPresent()) {
                response = new UserLoginResponse(jwt.get());
            }
        }
        catch (AuthenticationException | UserLoginException e) {
            loggingService.error(UserService.class,e.getMessage());
            throw e;
        }
        return Optional.ofNullable(response);
    }

    public HttpHeaders getJWTHeaders(String jwt) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return httpHeaders;
    }
}
