package com.deckbop.api.data;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public interface IUserDatasource {
    SqlRowSet getUserByLogin(String username);
    SqlRowSet getUserByEmail(String email);
    int registerUser(String username, String email, String password, String uuid);
    void activateUser(String activationToken);
    void updateUser(long user_id, String username, String password, String email);
    void deleteUser(long user_id);
}
