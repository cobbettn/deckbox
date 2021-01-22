package com.deckbop.app.exception;

public class CredentialsInUseException extends UserRegisterException {
    public CredentialsInUseException(String message) {
        super(message);
    }
}
