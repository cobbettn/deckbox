package com.deckbop.app.dao;

import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.beans.factory.annotation.Autowired;
import com.deckbop.app.security.model.User;

@Component
public class UserDAO  {

 @Autowired
    private JdbcTemplate jdbcTemplate;

    public User getUser (String username) {
        User user = null;
        try { 
            String sql = "SELECT pw FROM users where username = ?";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
            if (results.next()) {
                String pw = results.getString("pw");
                user = new User(1L,username, pw,"user",true);
            }
            else {
                System.out.println("user is not in the system");
            }
                    } catch (Exception e) {
               //unsure of what type(s) of exceptions may be thrown
        }
        return user;
    }
     public User registerUser(String username, String password) {
        User user = null;
        // check that user name isn't taken
        if (this.getUser(username) == null) {
            try {
                String sql = "INSERT INTO users (username, pw) VALUES (?,?)";
                jdbcTemplate.update(sql,username,password);
                user.setUsername(username);
                user.setPassword(password);
            }
                   catch (Exception e) {
                //unsure of what type(s) of exceptions may be thrown
            }
        }
        else {
            // user exits, return error mesasge
        }
        return user;
    }

}
            
        

