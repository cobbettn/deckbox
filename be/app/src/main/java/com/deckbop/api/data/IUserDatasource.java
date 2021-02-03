package com.deckbop.api.data;

import com.deckbop.api.controller.response.UpdateUserSuccessResponse;
import com.deckbop.api.model.User;

public interface IUserDatasource {
    User getUserByUsername(String username);
    User getUserByEmail(String email);
    int registerUser(String username, String email, String password, String uuid);
    void activateUser(String activationToken);
    void deleteActivationToken(String activationToken);
    UpdateUserSuccessResponse updateUserUsername(String username, long user_id);
    UpdateUserSuccessResponse updateUserEmail(String email, long user_id);
    UpdateUserSuccessResponse updateUserPassword(String password, long user_id);
    void deleteUser(long user_id);
}
