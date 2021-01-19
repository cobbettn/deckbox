package com.deckbop.app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LoggingService {
    private Logger logger = LoggerFactory.getLogger(LoggingService.class);

    public void trace(String message) {
        logger.trace(message);
    }
    public void info(String message) {
        logger.info(message);
    }
    public void warn(String message) {
        logger.warn(message);
    }
    public void debug(String message) {
        logger.debug(message);
    }
    public void error(String message) {
        logger.error(message);
    }
}
