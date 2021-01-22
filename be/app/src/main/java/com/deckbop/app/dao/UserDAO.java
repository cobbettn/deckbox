package com.deckbop.app.dao;

import com.deckbop.app.controller.request.UserUpdateRequest;
import com.deckbop.app.exception.CredentialAlreadyTakenException;
import com.deckbop.app.security.model.User;
import com.deckbop.app.service.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
public class UserDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    DeckDAO deckDAO;

    @Autowired
    LoggingService loggingService;

    // adding this in here since this is where the encryption will occur
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public Optional<User> getUserByLogin(String username) {
        User user = null;
        try {
            String sql = "SELECT * FROM user_account where username = ?";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
            if (results.next()) {
                Long id  = results.getLong("user_id");
                String name = results.getString("username");
                String pass = results.getString("pw");
                user = new User(id, name, pass, "user",true);
            }
        } catch (DataAccessException e) {
            loggingService.error("SQL error while getting user: username = " + username);
        }
        return Optional.ofNullable(user);
    }
    public Optional<String> getUsernameByEmail(String email) {
        String username = null;
        try {
            String sql = "SELECT username FROM user_account WHERE email = ?";
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, email);
            if (result.next()) {
                username = result.getString("username");
            }
        }
        catch (DataAccessException e) {
            loggingService.error("Sql error");
        }
        return Optional.ofNullable(username);
    }

    public void registerUser(String username, String email, String password) throws Exception {
        boolean nameTaken = this.getUserByLogin(username).isPresent();
        boolean emailTaken = this.getUsernameByEmail(email).isPresent();
        if (!nameTaken && !emailTaken) {
            String encryptedPassword = passwordEncoder().encode(password);
            String sql = "INSERT INTO user_account (username, pw, email, account_role, is_activated) VALUES (?, ?, ?, 'USER', FALSE)";
            jdbcTemplate.update(sql, username, encryptedPassword, email);
        }
        else {
            throw new CredentialAlreadyTakenException("credentials already registered");
        }
    }

    public void deleteUser(long user_id) {
        try {
            deckDAO.deleteUserDecks(user_id);
            String sql = "DELETE FROM user_account where user_id = ?";
            jdbcTemplate.update(sql, user_id);
        }
        catch (DataAccessException e) {
            loggingService.error("SQL error deleting user with id " + user_id);
        }
    }

    public void updateUser(long id, UserUpdateRequest request){
        try {
            Map<String, String> map = request.getCredentials();
            String encryptedPassword = passwordEncoder().encode(request.getPassword());
            String sql = "UPDATE user_account SET username = ?, pw = ?, email = ? WHERE user_id = ?";
            jdbcTemplate.update(sql, map.get("username"), encryptedPassword, map.get("email"), id);
        } catch (Exception e) {
            loggingService.error("SQL error while updating user.");
        }
    }



}
            
        

