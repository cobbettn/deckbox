package com.deckbop.api.data;

import com.deckbop.api.model.User;

public interface IUserDatasource {
    User getUserByUsername(String username);
    User getUserByEmail(String email);
    int registerUser(String username, String email, String password, String uuid);
    void activateUser(String activationToken);
    void updateUser(long user_id, String username, String password, String email);
    void deleteUser(long user_id);
}
