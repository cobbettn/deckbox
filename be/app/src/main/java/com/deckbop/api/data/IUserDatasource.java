package com.deckbop.api.data;

import com.deckbop.api.controller.response.UpdateUserSuccessResponse;
import com.deckbop.api.model.User;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public interface IUserDatasource {
    User getUserByUsername(String username);
    User getUserByEmail(String email);
    int registerUser(String username, String email, String password, String uuid);
    void activateUser(String activationToken);
    void deleteActivationToken(String activationToken);
    UpdateUserSuccessResponse updateUser(long user_id, String username, String password, String email);
    void deleteUser(long user_id);
}
