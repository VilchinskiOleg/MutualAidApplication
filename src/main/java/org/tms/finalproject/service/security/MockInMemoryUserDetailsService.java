package org.tms.finalproject.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MockInMemoryUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDetails worker = User
                .withDefaultPasswordEncoder()
                .username("worker")
                .password("worker")
                .roles("WORKER")
                .build();

        UserDetails customer = User
                .withDefaultPasswordEncoder()
                .username("customer")
                .password("customer")
                .roles("CUSTOMER")
                .build();

        if (username.equals("worker")) {
            return worker;
        } else if (username.equals("customer")) {
            return customer;
        }

        throw new RuntimeException();
    }
}
