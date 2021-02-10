package com.deckbop.api.data;

import org.springframework.stereotype.Component;

@Component
 public class SQLTemplates {
    // user table
    public static final String getUserByUsername =       "SELECT * FROM user_account where username = ?";
    public static final String getUserByEmail =          "SELECT * FROM user_account where email = ?";
    public static final String registerUser =            "INSERT INTO user_account (username, pw, email, account_role, is_activated, activation_token) VALUES (?, ?, ?, 'USER', FALSE, ?)";
    public static final String deleteUser =              "DELETE FROM user_account where user_id = ?";
    public static final String updateUserUsername =      "UPDATE user_account SET username = ? WHERE user_id = ? RETURNING username, pw, user_id, email";
    public static final String updateUserEmail =         "UPDATE user_account SET email = ? WHERE user_id = ? RETURNING username, pw, user_id, email";
    public static final String updateUserPassword =      "UPDATE user_account SET pw = ? WHERE user_id = ? RETURNING username, pw, user_id, email";
    public static final String activateUser =            "UPDATE user_account SET is_activated = true WHERE activation_token = ?";
    public static final String deleteActivationToken =   "UPDATE user_account SET activation_token = NULL WHERE activation_token = ?";
    // deck table
    public static final String getDeckById =             "SELECT deck_name, user_id FROM deck WHERE deck_id = ?";
    public static final String deleteDeck =              "DELETE FROM deck WHERE deck_id = ?";
    public static final String getDeckIdsByUserId =      "SELECT deck_id FROM deck WHERE user_id = ?";
    public static final String insertDeck =              "INSERT INTO deck(user_id, deck_name) VALUES (?, ?) RETURNING deck_id";
    public static final String updateDeckTable =         "UPDATE deck SET deck_name = ? WHERE deck_id = ?";
    public static final String checkDeckInDeckTable =    "SELECT * FROM deck WHERE user_id = ? AND deck_name = ?";
    // card table
    public static final String deleteCardsFromDeck =     "DELETE FROM card WHERE deck_id = ?";
    public static final String addCardsToDeck =          "INSERT INTO card (deck_id, card_id, card_quantity) VALUES ";
    public static final String getCardsByDeckId =        "SELECT card_id, card_quantity FROM card WHERE deck_id = ?";
}
