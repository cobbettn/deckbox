package com.deckbop.api.service;

import com.deckbop.api.controller.request.UserActivationRequest;
import com.deckbop.api.controller.request.UserLoginRequest;
import com.deckbop.api.controller.request.UserRegisterRequest;
import com.deckbop.api.controller.request.UserUpdateRequest;
import com.deckbop.api.controller.response.UserLoginResponse;
import com.deckbop.api.data.IUserDatasource;
import com.deckbop.api.exception.UserLoginException;
import com.deckbop.api.exception.UserRegisterException;
import com.deckbop.api.model.User;
import com.deckbop.api.security.jwt.JWTFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    LoggingService loggingService;

    @Autowired
    DeckService deckService;

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    @Qualifier("userDatasource")
    IUserDatasource userDatasource;

    @Bean
    PasswordEncoder getPasswordEncoder() {return new BCryptPasswordEncoder();}

    @Value("${deckbop.url.activation}")
    String activationUrl;

    public ResponseEntity<?> registerUser(UserRegisterRequest request) throws DataAccessException, UserRegisterException {
        ResponseEntity<?> response = null;
        boolean validRequest = this.validateRegisterRequest(request);
        if (validRequest) {
            String username = request.getCredentials().get("username");
            String email = request.getCredentials().get("email");
            String password = request.getPassword();
            String uuid = UUID.randomUUID().toString();
            int numRowsChanged = 0;
            while(numRowsChanged != 1){
                try {
                    numRowsChanged = userDatasource.registerUser(username, email, getPasswordEncoder().encode(password), uuid);
                } catch (DataAccessException e) {
                    uuid = UUID.randomUUID().toString();
                }
            }
            loggingService.info(this, username + "registered");
            try {
                mailSender.send(setRegistrationEmail(email, uuid));
            } catch (MailException e) {
                loggingService.error(this, "Error sending activation email");
            }
            loggingService.info(this, username + "sent email to " + email);
            response = new ResponseEntity<>(HttpStatus.CREATED);
        }
        else {
            throw new UserRegisterException("invalid register request");
        }
        return response;
    }

    public void activateUser(UserActivationRequest request) {
        userDatasource.activateUser(request.getActivation_token());
    }

    private UserLoginResponse tryLogin(UserLoginRequest request) {
        UserLoginResponse response = null;
        try {
            boolean validRequest = this.validateLoginRequest(request);
            if (validRequest) {
                User user = this.getUserFromCredentials(request.getCredentials());
                String jwt = authenticationService.authenticateAndGetJWTToken(user.getUsername(), request.getPassword());
                response = new UserLoginResponse(jwt, user.getId());
            }
        }
        catch (AuthenticationException | DataAccessException e) {
            loggingService.error(this, e.getMessage());
        }
        return response;
    }

    public ResponseEntity<?> loginUser(UserLoginRequest request) throws UserLoginException {
        ResponseEntity<?> response = null;
        UserLoginResponse userLoginResponse = this.tryLogin(request);
        if (Optional.ofNullable(userLoginResponse).isPresent()) {
            HttpHeaders httpHeaders = this.getJWTHeaders(userLoginResponse.getJwtToken());
            response = new ResponseEntity<>(userLoginResponse, httpHeaders, HttpStatus.OK);
        }
        else {
            throw new UserLoginException("failed authentication attempt");
        }
        return response;
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

    public User getUserByUsername(String username) {
        User user = null;
        try {
            user = userDatasource.getUserByUsername(username);
        } catch (DataAccessException e) {
            loggingService.info(this,"Could not find user with username: " + username);
        }
        return user;
    }

    public User getUserByEmail(String email) {
        User user = null;
        try {
            user = userDatasource.getUserByEmail(email);
        } catch (DataAccessException e) {
            loggingService.info(this,"Could not find user with email: " + email);
        }
        return user;
    }

    public User getUserFromCredentials(Map<String, String> credentials) {
        User user = null;
        String username = credentials.get("username");
        String email = credentials.get("email");
        try {
            user = userDatasource.getUserByUsername(username);
            if (Optional.ofNullable(user).isEmpty()) {
                user = userDatasource.getUserByEmail(email);
            }
        }
        catch (EmptyResultDataAccessException e) {
            loggingService.error(this,"validation error: tried to fetch non-existent user");
        }
        return user;
    }

    private boolean loginAndRegisterRequestValidator(String type, UserRegisterRequest request) {
        boolean isValid = false;
        boolean isRegister = type.equals("register");
        User emailUser, usernameUser;
        String username, email;
        username = request.getCredentials().get("username");
        email = request.getCredentials().get("email");
        if (isRegister ?
                Optional.ofNullable(username).isPresent() && Optional.ofNullable(email).isPresent() :
                Optional.ofNullable(username).isPresent() || Optional.ofNullable(email).isPresent()
        ) {
            emailUser = this.getUserByEmail(email);
            usernameUser = this.getUserByUsername(username);
            isValid = isRegister?
                    Optional.ofNullable(emailUser).isEmpty() && Optional.ofNullable(usernameUser).isEmpty() :
                    Optional.ofNullable(emailUser).isPresent() || Optional.ofNullable(usernameUser).isPresent();
        }
        return isValid;
    }

    private boolean validateRegisterRequest(UserRegisterRequest request) {
        return loginAndRegisterRequestValidator("register", request);
    }

    private boolean validateLoginRequest(UserLoginRequest request) {
        return loginAndRegisterRequestValidator("login", request);
    }

    private SimpleMailMessage setRegistrationEmail(String emailAddress, String token){
        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setTo(emailAddress);
        emailMessage.setSubject("Registration Confirmation Email From DeckBop ");
        emailMessage.setText("Thank you for registering with DeckBop \nClick here to activate:  " + activationUrl + "?token=" + token);
        return emailMessage;
    }

    private HttpHeaders getJWTHeaders(String jwt) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return httpHeaders;
    }
}
