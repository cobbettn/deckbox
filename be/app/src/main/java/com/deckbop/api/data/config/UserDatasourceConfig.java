package com.deckbop.api.data.config;

import com.deckbop.api.data.IUserDatasource;
import com.deckbop.api.data.dao.impl.UserDatabaseDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class UserDatasourceConfig {
    @Profile({"dev", "prod"})
    @Bean(name = "userDatasource")
    public IUserDatasource userDatabaseDAO() {
        return new UserDatabaseDAO();
    }

    @Profile("test")
    @Bean(name = "userDatasource")
    public IUserDatasource testDatasource() {
        return null;
    }
}
