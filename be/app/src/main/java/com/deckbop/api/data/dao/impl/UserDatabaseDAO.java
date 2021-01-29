package com.deckbop.api.data.dao.impl;

import com.deckbop.api.data.IUserDatasource;
import com.deckbop.api.data.SQLTemplates;
import com.deckbop.api.data.dao.DatabaseDAO;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class UserDatabaseDAO extends DatabaseDAO implements IUserDatasource  {
    @Override
    public int registerUser(String username, String email, String password, String uuid) {
        return this.jdbcTemplate.update(SQLTemplates.registerUser, username, password, email, uuid);
    }

    @Override
    public void activateUser(String activationToken){
        this.jdbcTemplate.update(SQLTemplates.activateUser, activationToken);
    }

    @Override
    public SqlRowSet getUserByLogin(String username) {
        return this.jdbcTemplate.queryForRowSet(SQLTemplates.getUserByLogin, username);
    }

    @Override
    public SqlRowSet getUserByEmail(String email) {
        return this.jdbcTemplate.queryForRowSet(SQLTemplates.getUserByEmail, email);
    }

    @Override
    public void updateUser(long user_id, String username, String password, String email) {
        this.jdbcTemplate.update(SQLTemplates.updateUser, username, password, email, user_id);
    }

    @Override
    public void deleteUser(long user_id) {
        this.jdbcTemplate.update(SQLTemplates.deleteUser, user_id);
    }
}
            
        

