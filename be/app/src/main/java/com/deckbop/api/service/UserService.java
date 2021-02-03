package com.deckbop.api.service;

import com.deckbop.api.controller.request.UserActivationRequest;
import com.deckbop.api.controller.request.UserLoginRequest;
import com.deckbop.api.controller.request.UserRegisterRequest;
import com.deckbop.api.controller.request.UserUpdateRequest;
import com.deckbop.api.controller.response.UpdateUserSuccessResponse;
import com.deckbop.api.controller.response.UserLoginErrorResponse;
import com.deckbop.api.controller.response.UserLoginSuccessResponse;
import com.deckbop.api.controller.response.UserRegisterErrorResponse;
import com.deckbop.api.data.IUserDatasource;
import com.deckbop.api.model.User;
import com.deckbop.api.security.jwt.JWTFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
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

import java.util.ArrayList;
import java.util.List;
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
    PasswordEncoder
    getPasswordEncoder() {return new BCryptPasswordEncoder();}

    @Value("${deckbop.url.activation}")
    String activationUrl;

    public ResponseEntity<?> registerUser(UserRegisterRequest request) {
        ResponseEntity<?> response;
        List<String> errorList = this.validateRegisterRequest(request);
        if (errorList.isEmpty()) {
            String username = request.getCredentials().get("username");
            String email = request.getCredentials().get("email");
            String password = request.getPassword();
            String uuid = UUID.randomUUID().toString();
            int maxAttempts = 10;
            while (maxAttempts > 0) {
                try {
                    int numRowsChanged = userDatasource.registerUser(username, email, getPasswordEncoder().encode(password), uuid);
                    if (numRowsChanged == 1) {
                        loggingService.info(this, username + "registered");
                        mailSender.send(this.setRegistrationEmail(email, uuid));
                        loggingService.info(this, username + "sent email to " + email);
                        break;
                    }
                } catch (MailException e) {
                    loggingService.error(this, "Error sending activation email");
                } catch (DataAccessException e) {
                    uuid = UUID.randomUUID().toString();
                }
                maxAttempts--;
            }
            if (maxAttempts == 0) {
                loggingService.warn(this, "10 requests were made to send an registration  email to " + email);
            }
            response = new ResponseEntity<>(HttpStatus.CREATED);
        }
        else {
            UserRegisterErrorResponse userRegisterErrorResponse = new UserRegisterErrorResponse(errorList);
            response = new ResponseEntity<>(userRegisterErrorResponse, HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    public void activateUser(UserActivationRequest request) {
        userDatasource.activateUser(request.getActivation_token());
        userDatasource.deleteActivationToken(request.getActivation_token());
    }

    public ResponseEntity<?> loginUser(UserLoginRequest request) {
        ResponseEntity<?> response = null;
        List<String> errorList = this.validateLoginRequest(request);
        if (errorList.isEmpty()) {
            User user = this.tryLoggingInUser(request); // check user and password
             boolean loginSuccessful = Optional.ofNullable(user).isPresent();
            if (loginSuccessful) {
                boolean isActivated = user.isActivated();
                if (isActivated) {
                    String jwt;
                    try {
                        jwt = authenticationService.authenticateAndGetJWTToken(user.getUsername(), request.getPassword());
                        UserLoginSuccessResponse userLoginSuccessResponse = new UserLoginSuccessResponse(jwt, user.getId(), user.getUsername(), user.getEmail(), user.getPassword());
                        HttpHeaders httpHeaders = this.getJWTHeaders(jwt);
                        response = new ResponseEntity<>(userLoginSuccessResponse, httpHeaders, HttpStatus.OK);
                    }
                    catch (AuthenticationException e) {
                        loggingService.error(this, "login validation failure");
                        throw e;
                    }
                }
                else {
                    errorList.add("user not activated");
                }
            }
            else {
                errorList.add("incorrect password");
            }
        }
        if (Optional.ofNullable(response).isEmpty()) {
            UserLoginErrorResponse userLoginErrorResponse = new UserLoginErrorResponse(errorList);
            response = new ResponseEntity<>(userLoginErrorResponse, HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    public ResponseEntity<?> updateUserById (long user_id, UserUpdateRequest request) {
        ResponseEntity<?> response = null;
        try {
            Optional<String> username = Optional.ofNullable(request.getCredentials().get("username"));
            Optional<String> email = Optional.ofNullable(request.getCredentials().get("email"));
            Optional<String> password = Optional.ofNullable(request.getPassword());
            if (username.isPresent()) {
                UpdateUserSuccessResponse successResponse =  userDatasource.updateUserUsername(username.get(), user_id);
                response = new ResponseEntity<>(successResponse, HttpStatus.OK);
            }
            if (email.isPresent()) {
                UpdateUserSuccessResponse successResponse =  userDatasource.updateUserEmail(email.get(), user_id);
                response = new ResponseEntity<>(successResponse, HttpStatus.OK);
            }
            if (password.isPresent()) {
                UpdateUserSuccessResponse successResponse =  userDatasource.updateUserPassword(this.getPasswordEncoder().encode(password.get()), user_id);
                response = new ResponseEntity<>(successResponse, HttpStatus.OK);
            }
        }
        catch (DataAccessException e) {
            loggingService.error(this,"SQL Error while updating user: " +  e.getMessage());
            throw e;
        }
        return response;
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
            loggingService.info(this,"Tried to find user with username: " + username);
        }
        return user;
    }

    private User getUserByEmail(String email) {
        User user = null;
        try {
            user = userDatasource.getUserByEmail(email);
        } catch (DataAccessException e) {
            loggingService.info(this,"Tried to find user with email: " + email);
        }
        return user;
    }

    private User tryLoggingInUser(UserLoginRequest request) {
        User user = null;
        String username = request.getCredentials().get("username");
        String email = request.getCredentials().get("email");
        String password = request.getPassword();
        if (Optional.ofNullable(username).isPresent()) {
            user = this.loginUserByUsername(username, password);
        }
        if (Optional.ofNullable(user).isEmpty() && Optional.ofNullable(email).isPresent()) {
            user = this.loginUserByEmail(email, password);
        }
        return user;
    }

    private User loginUserByUsername (String username, String password) {
        User user = this.getUserByUsername(username);
        if (Optional.ofNullable(user).isPresent() && !this.getPasswordEncoder().matches(password, user.getPassword())) {
            user = null;
        }
        return user;
    }

    private User loginUserByEmail(String email, String password) {
        User user = this.getUserByEmail(email);
        if (Optional.ofNullable(user).isPresent() && !this.getPasswordEncoder().matches(password, user.getPassword())) {
            user = null;
        }
        return user;
    }

    private List<String> loginAndRegisterRequestValidator(String type, UserRegisterRequest request) {
        String username = request.getCredentials().get("username");
        String email = request.getCredentials().get("email");
        boolean isRegister = type.equals("register");
        boolean hasEmail = Optional.ofNullable(email).isPresent();
        boolean hasUsername = Optional.ofNullable(username).isPresent();
        boolean hasEmailUser = Optional.ofNullable(this.getUserByEmail(email)).isPresent();
        boolean hasUsernameUser = Optional.ofNullable(this.getUserByUsername(username)).isPresent();
        boolean hasPassword = Optional.ofNullable(request.getPassword()).isPresent();

        return isRegister ?
                getRegisterErrors(hasUsername, hasEmail, hasEmailUser, hasUsernameUser):
                getLoginErrors(hasUsername, hasEmail, hasEmailUser, hasUsernameUser, hasPassword);
    }

    private List<String> getRegisterErrors(
            boolean hasUsername,
            boolean hasEmail,
            boolean hasEmailUser,
            boolean hasUsernameUser
    ) {
        List<String> errorList = new ArrayList<>();
        if (!hasUsername) {
            errorList.add("no username provided");
        }
        if (!hasEmail) {
            errorList.add("no email provided");
        }
        if (hasEmailUser) {
            errorList.add("email taken");
        }
        if (hasUsernameUser) {
            errorList.add("username taken");
        }
        return errorList;
    }

    private List<String> getLoginErrors(
            boolean hasUsername,
            boolean hasEmail,
            boolean hasEmailUser,
            boolean hasUsernameUser,
            boolean hasPassword
    ) {
        List<String> errorList = new ArrayList<>();
        if (!hasEmail && !hasUsername) {
            errorList.add("No credentials provided");
        }
        if (!hasPassword) {
            errorList.add("No credentials provided");
        }
        if (hasUsername && !hasUsernameUser) {
            errorList.add("username not registered");
        }
        if (hasEmail && !hasEmailUser) {
            errorList.add("email not registered");
        }
        return  errorList;
    }

    private List<String> validateRegisterRequest(UserRegisterRequest request) {
        return loginAndRegisterRequestValidator("register", request);
    }

    private List<String> validateLoginRequest(UserLoginRequest request) {
        return loginAndRegisterRequestValidator("login", request);
    }

    private SimpleMailMessage setRegistrationEmail(String emailAddress, String token){
        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setTo(emailAddress);
        emailMessage.setSubject("Registration Confirmation Email From DeckBop ");
        emailMessage.setText("Thank you for registering with DeckBop \nClick here to activate:  " + activationUrl + "/" + token);
        return emailMessage;
    }

    private HttpHeaders getJWTHeaders(String jwt) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return httpHeaders;
    }
}
