package com.deckbop.app.exception;

public class NoUsernameOrEmailException extends UserLoginException {
    public NoUsernameOrEmailException(String message) { super(message); }
}
