package com.deckbop.api.data.dao.impl.rowmapper;

import com.deckbop.api.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        Long userId = resultSet.getLong("user_id");
        String username = resultSet.getString("username");
        String password = resultSet.getString("pw");
        String email = resultSet.getString("email");
        String role = resultSet.getString("account_role");
        boolean isActivated = resultSet.getBoolean("is_activated");
        return new User(userId, username, password, email, role, isActivated);
    }
}


