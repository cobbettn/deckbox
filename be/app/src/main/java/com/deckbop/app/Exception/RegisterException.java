package com.deckbop.app.Exception;

public class RegisterException extends Exception {
    public RegisterException() {
        super("Error registering user");
    }
    public RegisterException(String message) {
        super(message);
    }
}
