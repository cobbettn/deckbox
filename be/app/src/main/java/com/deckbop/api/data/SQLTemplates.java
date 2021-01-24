package com.deckbop.api.data;

import org.springframework.stereotype.Component;

@Component
 public class SQLTemplates {
    public static final String getUserByLogin =    "SELECT * FROM user_account where username = ?";
    public static final String getUserByEmail =    "SELECT * FROM user_account where email = ?";
    public static final String registerUser =      "INSERT INTO user_account (username, pw, email, account_role, is_activated) VALUES (?, ?, ?, 'USER', FALSE)";
    public static final String deleteUser =        "DELETE FROM user_account where user_id = ?";
    public static final String updateUser =        "UPDATE user_account SET username = ?, pw = ?, email = ? WHERE user_id = ?";
}
