package com.deckbop.api.service;

import com.deckbop.api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class UserModelDetailsService implements UserDetailsService {
    @Autowired
    UserService userService;
    @Autowired
    LoggingService loggingService;

    @Override
    public UserDetails loadUserByUsername(final String username) {
        loggingService.info(this, "loading user: " + username);
        User user;
        try {
            user = userService.getUserByUsername(username);
        }
        catch (Exception e ) {
            throw new UsernameNotFoundException("User " + username + " was not found.");
        }
        return createSpringSecurityUser(username, user);
    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(String lowercaseLogin, User user) {
//        if (!user.isActivated()) {
//            throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
//        }
        List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                grantedAuthorities);
    }
}