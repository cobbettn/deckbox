package com.deckbop.app.exception;

public class EmailNotRegisteredException extends UserLoginException {
    public EmailNotRegisteredException(String message) {
        super(message);
    }
}
