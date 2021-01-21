package com.deckbop.app.exception;

public class NoUsernameOrEmailExcecption extends UserLoginException {
    public NoUsernameOrEmailExcecption(String message) {
        super(message);
    }
}
