package com.deckbop.api.data.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public abstract class DatabaseDAO {
    @Autowired
    protected JdbcTemplate jdbcTemplate;
}
