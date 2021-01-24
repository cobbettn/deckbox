package com.deckbop.api.exception;

public class CredentialsInUseException extends UserRegisterException {
    public CredentialsInUseException(String message) {
        super(message);
    }
}
