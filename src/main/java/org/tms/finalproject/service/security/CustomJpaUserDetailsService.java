package org.tms.finalproject.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.tms.finalproject.entity.User;
import org.tms.finalproject.repository.UserRepository;
import java.util.Arrays;
import java.util.Optional;

@Service
public class CustomJpaUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUserName(s);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User with such login does not exist!");
        }
        return new org.springframework.security.core.userdetails.User(
                userOptional.get().getUserName(),
                userOptional.get().getPassword(),
                true,
                true,
                true,
                true,
                Arrays.asList(new SimpleGrantedAuthority(String.format("ROLE_%s", userOptional.get().getRole()))));
    }
}
