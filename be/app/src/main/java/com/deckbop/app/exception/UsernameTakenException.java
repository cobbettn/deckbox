package com.deckbop.app.exception;

public class UsernameTakenException extends Exception {
    public UsernameTakenException() {
        super("User name taken");
    }
    public UsernameTakenException(String message) {
        super(message);
    }
}
