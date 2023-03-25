package com.example.springmovieapi.security.service;

import com.example.springmovieapi.domain.User;
import com.example.springmovieapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Service implementation for retrieving user details by username.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * The user repository used for retrieving user data.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     *
     * @param username the username identifying the user whose data is required
     * @return the user details for the specified username
     * @throws UsernameNotFoundException if no user is found with the specified username
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),user.getPassword(),new ArrayList<>());
    }
}
