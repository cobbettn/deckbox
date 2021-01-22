package com.deckbop.app.service.impl;

import com.deckbop.app.service.LoggingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoggingServiceImpl implements LoggingService {
    final private Logger logger = LoggerFactory.getLogger(LoggingServiceImpl.class);
    @Override
    public void trace(String message) {
        logger.trace(message);
    }
    @Override
    public void info(String message) {
        logger.info(message);
    }
    @Override
    public void warn(String message) {
        logger.warn(message);
    }
    @Override
    public void debug(String message) {
        logger.debug(message);
    }
    @Override
    public void error(String message) {
        logger.error(message);
    }
}
