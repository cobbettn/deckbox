package com.deckbop.api.data.dao.impl.rowmapper;

import com.deckbop.api.controller.response.UpdateUserSuccessResponse;
import com.deckbop.api.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateUserResponseRowMapper implements RowMapper<UpdateUserSuccessResponse> {
    @Override
    public UpdateUserSuccessResponse mapRow(ResultSet resultSet, int i) throws SQLException {
        Long userId = resultSet.getLong("user_id");
        String username = resultSet.getString("username");
        String password = resultSet.getString("pw");
        String email = resultSet.getString("email");
        return new UpdateUserSuccessResponse(userId, username, password, email);
    }
}
