package com.example.Debate.service;

import com.example.Debate.model.User;
import com.example.Debate.jwt.UserPrincipal;
import com.example.Debate.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserPrincipalService implements UserDetailsService {

    private UserRepository userRepository;

    public UserPrincipalService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) {
        User user = userRepository.findUserByLogin(s).orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found", s)));
        return new UserPrincipal(user.getLogin(),user.getPassword(),user.getRole());
    }
}
