package com.tcc.Hive.security.service;

import com.tcc.Hive.user.UserHive;
import com.tcc.Hive.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserAuthenticateService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if(!email.isEmpty()) {
            UserHive userHive = userRepository.findByEmail(email);
            UserDetails userDetails = new User(userHive.getEmail(), userHive.getPassword(), new ArrayList<>());
            return userDetails;
        } else {
            throw new RuntimeException("usuário invalido");
        }
    }
}
