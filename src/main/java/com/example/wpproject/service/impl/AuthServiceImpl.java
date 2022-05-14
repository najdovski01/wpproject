package com.example.wpproject.service.impl;

import com.example.wpproject.model.User;
import com.example.wpproject.model.exceptions.InvalidArgumentsException;
import com.example.wpproject.model.exceptions.InvalidUserCredentialsException;
import com.example.wpproject.repository.UserRepository;
import com.example.wpproject.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User login(String username, String password) {
        if(username == null || username.isEmpty() || password == null || password.isEmpty()) {
            throw new InvalidArgumentsException();
        }
        return this.userRepository.findByUsernameAndPassword(username, password)
                .orElseThrow(InvalidUserCredentialsException::new);
    }
}
