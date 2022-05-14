package com.example.wpproject.service;

import com.example.wpproject.model.User;

public interface AuthService {
    User login(String username, String password);
}
