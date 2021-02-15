package com.deckbop.api.data.dao.impl;

import com.deckbop.api.controller.response.UpdateUserResponse;
import com.deckbop.api.data.IUserDatasource;
import com.deckbop.api.data.SQLTemplates;
import com.deckbop.api.data.dao.DatabaseDAO;
import com.deckbop.api.data.dao.impl.rowmapper.UpdateUserResponseRowMapper;
import com.deckbop.api.data.dao.impl.rowmapper.UserRowMapper;
import com.deckbop.api.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserDatabaseDAO extends DatabaseDAO implements IUserDatasource {
    @Override
    public int registerUser(String username, String email, String password, String uuid) {
        return this.jdbcTemplate.update(SQLTemplates.registerUser, username, password, email, uuid);
    }

    @Override
    public void activateUser(String activationToken){
        this.jdbcTemplate.update(SQLTemplates.activateUser, activationToken);
    }

    @Override
    public void deleteActivationToken(String activationToken){
        this.jdbcTemplate.update(SQLTemplates.deleteActivationToken, activationToken);
    }

    @Override
    public User getUserByUsername(String username) {
        return this.jdbcTemplate.queryForObject(SQLTemplates.getUserByUsername, new UserRowMapper(), username);
    }

    @Override
    public User getUserByEmail(String email) {
        return this.jdbcTemplate.queryForObject(SQLTemplates.getUserByEmail, new UserRowMapper(), email);
    }

    @Override
    public UpdateUserResponse updateUserUsername(String username, long user_id) {
        return this.jdbcTemplate.queryForObject(SQLTemplates.updateUserUsername, new UpdateUserResponseRowMapper(), username, user_id);
    }

    @Override
    public UpdateUserResponse updateUserEmail(String email, long user_id) {
        return this.jdbcTemplate.queryForObject(SQLTemplates.updateUserEmail, new UpdateUserResponseRowMapper(), email, user_id);
    }

    @Override
    public UpdateUserResponse updateUserPassword(String password, long user_id) {
        return this.jdbcTemplate.queryForObject(SQLTemplates.updateUserPassword, new UpdateUserResponseRowMapper(), password, user_id);
    }

    @Override
    public void deleteUser(long user_id) {
        this.jdbcTemplate.update(SQLTemplates.deleteUser, user_id);
    }
}
            
        

