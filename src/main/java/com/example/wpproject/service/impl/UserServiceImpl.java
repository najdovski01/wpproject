package com.example.wpproject.service.impl;

import com.example.wpproject.model.User;
import com.example.wpproject.model.enumerations.Role;
import com.example.wpproject.model.exceptions.InvalidUsernameOrPasswordException;
import com.example.wpproject.model.exceptions.PasswordsDoNotMatchException;
import com.example.wpproject.model.exceptions.UsernameAlreadyExistsException;
import com.example.wpproject.repository.UserRepository;
import com.example.wpproject.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException(username));
    }


    @Override
    public User register(String name, String surname, String username, String email, String password, String repeatPassword, Role role) {
        if (username==null || username.isEmpty()  || password==null || password.isEmpty())
            throw new InvalidUsernameOrPasswordException();
        if (!password.equals(repeatPassword))
            throw new PasswordsDoNotMatchException();
        if(this.userRepository.findByUsername(username).isPresent())
            throw new UsernameAlreadyExistsException(username);
        User user = new User(name, surname, username, email, passwordEncoder.encode(password), role);
        return userRepository.save(user);
    }
}
