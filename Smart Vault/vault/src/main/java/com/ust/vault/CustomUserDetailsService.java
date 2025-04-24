package com.ust.vault;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch user details from the database (Replace with actual repository call)
        if (!"admin".equals(username)) {
            throw new UsernameNotFoundException("User not found");
        }

        // Return UserDetails object (For testing, using an in-memory user)
        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password("{noop}password") // Use {noop} for plain text passwords
                .roles("USER")
                .build();
    }
}

