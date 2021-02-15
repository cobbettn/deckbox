package com.deckbop.api.data.dao.impl.rowmapper;

import com.deckbop.api.controller.response.UpdateUserResponse;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateUserResponseRowMapper implements RowMapper<UpdateUserResponse> {
    @Override
    public UpdateUserResponse mapRow(ResultSet resultSet, int i) throws SQLException {
        Long userId = resultSet.getLong("user_id");
        String username = resultSet.getString("username");
        String password = resultSet.getString("pw");
        String email = resultSet.getString("email");
        return new UpdateUserResponse(userId, username, password, email);
    }
}
