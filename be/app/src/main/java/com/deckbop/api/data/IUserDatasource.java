package com.deckbop.api.data;

import com.deckbop.api.controller.request.DeckRequest;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public interface IUserDatasource {
    public void registerUser(String username, String email, String password);
    public SqlRowSet getUserByLogin(String username);
    public SqlRowSet getUserByEmail(String email);
    public void updateUser(long user_id, String username, String password, String email);
    public void deleteUser(long user_id);
}
