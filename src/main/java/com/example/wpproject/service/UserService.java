package com.example.wpproject.service;

import com.example.wpproject.model.User;
import com.example.wpproject.model.enumerations.Role;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User register(String name, String surname, String email, String username, String password, String repeatPassword, Role role);
}
