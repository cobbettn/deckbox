package com.deckbop.api.service;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoggingService {
    public void trace(Object object, String message) {
        LoggerFactory.getLogger(object.getClass()).trace(message);
    }
    public void info(Object object, String message) { LoggerFactory.getLogger(object.getClass()).info(message); }
    public void warn(Object object, String message) {
        LoggerFactory.getLogger(object.getClass()).warn(message);
    }
    public void debug(Object object, String message) {
        LoggerFactory.getLogger(object.getClass()).debug(message);
    }
    public void error(Object object, String message) {
        LoggerFactory.getLogger(object.getClass()).error(message);
    }
}
