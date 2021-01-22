package com.deckbop.app.dao;

import com.deckbop.app.controller.request.UserUpdateRequest;
import com.deckbop.app.model.User;
import com.deckbop.app.service.impl.DeckServiceImpl;
import com.deckbop.app.service.impl.LoggingServiceImpl;
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
    DeckServiceImpl deckService;

    @Autowired
    LoggingServiceImpl loggingService;

    // adding this in here since this is where the encryption will occur
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public Optional<User> getUserByLogin(String username) {
        User user = null;
        String sql = "SELECT * FROM user_account where username = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
        if (results.next()) {
            Long id  = results.getLong("user_id");
            String name = results.getString("username");
            String pass = results.getString("pw");
            user = new User(id, name, pass, "user",true);
        }
        return Optional.ofNullable(user);
    }

    public Optional<User> getUserByEmail(String email) {
        User user = null;
        String sql = "SELECT * FROM user_account where email = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, email);
        if (results.next()) {
            Long id  = results.getLong("user_id");
            String name = results.getString("username");
            String pass = results.getString("pw");
            user = new User(id, name, pass, "user",true);
        }
        return Optional.ofNullable(user);
    }

    public void registerUser(String username, String email, String password) throws DataAccessException {
        String encryptedPassword = passwordEncoder().encode(password);
        String sql = "INSERT INTO user_account (username, pw, email, account_role, is_activated) VALUES (?, ?, ?, 'USER', FALSE)";
        jdbcTemplate.update(sql, username, encryptedPassword, email);
    }

    public void deleteUser(long user_id) throws DataAccessException {
        deckService.deleteUserDecks(user_id);
        String sql = "DELETE FROM user_account where user_id = ?";
        jdbcTemplate.update(sql, user_id);
    }

    public void updateUser(long id, UserUpdateRequest request) throws DataAccessException{
        Map<String, String> map = request.getCredentials();
        String encryptedPassword = passwordEncoder().encode(request.getPassword());
        String sql = "UPDATE user_account SET username = ?, pw = ?, email = ? WHERE user_id = ?";
        jdbcTemplate.update(sql, map.get("username"), encryptedPassword, map.get("email"), id);
    }
}
            
        

