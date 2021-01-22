package com.deckbop.app.service;

public interface LoggingService {
    void trace(String message);

    void info(String message);

    void warn(String message);

    void debug(String message);

    void error(String message);
}
