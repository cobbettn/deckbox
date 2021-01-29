package com.deckbop.api.service;

import com.deckbop.api.controller.request.UserLoginRequest;
import com.deckbop.api.controller.request.UserRegisterRequest;
import com.deckbop.api.controller.request.UserUpdateRequest;
import com.deckbop.api.controller.response.UserLoginResponse;
import com.deckbop.api.data.IUserDatasource;
import com.deckbop.api.exception.CredentialsInUseException;
import com.deckbop.api.exception.UserLoginException;
import com.deckbop.api.model.User;
import com.deckbop.api.security.jwt.JWTFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    LoggingService loggingService;

    @Autowired
    DeckService deckService;

    @Autowired
    @Qualifier("userDatasource")
    IUserDatasource userDatasource;

    @Bean
    PasswordEncoder getPasswordEncoder() {return new BCryptPasswordEncoder();}

    public User getUserByUsername(String username) {
        User user;
        try {
            user = userDatasource.getUserByUsername(username);
        } catch (DataAccessException e) {
            loggingService.error(this,"SQL error while getting user: username = " + username);
            throw e;
        }
        return user;
    }

    public User getUserByEmail(String email) {
        User user;
        try {
            user = userDatasource.getUserByEmail(email);
        } catch (DataAccessException e) {
            loggingService.error(this,"SQL error while getting user: email = " + email);
            throw e;
        }
        return user;
    }

    public ResponseEntity<?> registerUser(UserRegisterRequest request) throws DataAccessException, CredentialsInUseException {
        Optional<String> email = Optional.ofNullable(request.getCredentials().get("email"));
        Optional<String> username  = Optional.ofNullable(request.getCredentials().get("username"));
        if (email.isPresent() && username.isPresent()) {
            User user;
            boolean emailNotTaken;
            boolean usernameNotTaken;
            try {
                user = userDatasource.getUserByEmail(email.get());
                emailNotTaken = Optional.ofNullable(user).isPresent();
            }
            catch (DataAccessException e) {
                emailNotTaken = true;
            }
            try {
               user = userDatasource.getUserByUsername(username.get());
               usernameNotTaken = Optional.ofNullable(user).isPresent();
            }
            catch (DataAccessException e) {
                usernameNotTaken = true;
            }
            if (emailNotTaken && usernameNotTaken) {
                try {
                    userDatasource.registerUser(username.get(), email.get(), getPasswordEncoder().encode(request.getPassword()));
                    return new ResponseEntity<>(HttpStatus.CREATED);
                }
                catch (DataAccessException e) {
                    loggingService.error(this,"SQL error while registering a user");
                    throw e;
                }
            }
            else {
                loggingService.error(this,"Credentials already in use");
                if (!emailNotTaken && !usernameNotTaken) {
                    throw new CredentialsInUseException("username and email already taken");
                }
                else if (!emailNotTaken) {
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
            loggingService.error(this,"SQL error while deleting user");
            throw e;
        }
    }

    public void updateUser(long user_id, UserUpdateRequest request) {
        try {
            Optional<String> username = Optional.ofNullable(request.getCredentials().get("username"));
            Optional<String> email = Optional.ofNullable(request.getCredentials().get("email"));
            Optional<String> password = Optional.ofNullable(request.getPassword());
            if (username.isPresent() && email.isPresent() && password.isPresent()) {
                userDatasource.updateUser(user_id, username.get(), getPasswordEncoder().encode(password.get()), email.get());
            }
        }
        catch (DataAccessException e) {
            loggingService.error(this,"SQL Error while updating user");
            throw e;
        }
    }

    public Optional<UserLoginResponse> loginUser(UserLoginRequest request) throws UserLoginException {
        UserLoginResponse response = null;
        try {
            Optional<User> credentialsUser = this.getUserFromCredentials(request.getCredentials());
            if (credentialsUser.isPresent()) {
                User user = credentialsUser.get();
                Optional<String> jwt = Optional.ofNullable(authenticationService.authenticateAndGetJWTToken(user.getUsername(), request.getPassword()));
                if (jwt.isPresent()) {
                    response = new UserLoginResponse(jwt.get(), user.getId());
                }
            }
            else {
                throw new UserLoginException("could not load user from credentials");
            }
        }
        catch (AuthenticationException | DataAccessException | UserLoginException e) {
            loggingService.error(this, e.getMessage());
            throw e;
        }
        return Optional.ofNullable(response);
    }

    public Optional<User> getUserFromCredentials(Map<String, String> credentials) throws UserLoginException {
        User user = null;
        Optional<String> username = Optional.ofNullable(credentials.get("username"));
        Optional<String> email = Optional.ofNullable(credentials.get("email"));
        boolean validUsername = true, validEmail = true;
        if (username.isPresent()) {
            try {
                user = userDatasource.getUserByUsername(username.get());
            }
            catch (EmptyResultDataAccessException e) {
                validUsername = false;
            }
        }
        if (Optional.ofNullable(user).isEmpty() && email.isPresent()) {
            try {
                user = userDatasource.getUserByEmail(email.get());
            }
            catch (EmptyResultDataAccessException e) {
                validEmail = false;
            }
        }
        if (!validUsername && !validEmail) {
            throw new UserLoginException("bad email and username");
        }
        if (!validEmail) {
            throw new UserLoginException("bad email");
        }
        if (!validUsername) {
            throw new UserLoginException("bad username");
        }
        return Optional.ofNullable(user);
    }

    public HttpHeaders getJWTHeaders(String jwt) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return httpHeaders;
    }
}
