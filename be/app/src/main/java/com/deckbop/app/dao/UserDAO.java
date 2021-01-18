package com.deckbop.app.dao;

import com.deckbop.app.controller.dto.RegisterDto;
import com.deckbop.app.exception.UsernameTakenException;
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

import java.util.Optional;

@Component
public class UserDAO  {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    LoggingService loggingService;

    // adding this in here since this is where the encryption will occur
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public Optional<User> getUser (String username) {
        User user = null;

        try {
            String sql = "SELECT * FROM users where username = ?";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
            if (results.next()) {
                Long id  = results.getLong("user_id");
                String name = results.getString("username");
                String pass = results.getString("pw");
                user = new User(id, name, pass, "user",true);
            }
        } catch (DataAccessException e) {
            loggingService.error(this.getClass().getName() + ".getUser() could not access database");
        }

        return Optional.ofNullable(user);
    }

    public void createUser(RegisterDto registerDto) throws UsernameTakenException {
        String username = registerDto.getUsername();
        Optional<User> user = this.getUser(username);
        if (user.isEmpty()) {
            try {
                String password = registerDto.getPassword();
                String encryptedPassword = passwordEncoder().encode(password); // encrypt password before storing in db
                String sql = "INSERT INTO users (username, pw) VALUES (?, ?)";
                jdbcTemplate.update(sql, username, encryptedPassword);
            }
            catch (DataAccessException e) {
                loggingService.error(this.getClass().getName() + ".createUser() : could not access database");
            }
        }
        else {
            throw new UsernameTakenException();
        }
    }

}
            
        

