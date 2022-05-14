package com.example.wpproject.service;

import com.example.wpproject.model.Author;
import com.example.wpproject.model.User;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    Optional<Author> findById(Long id);
    List<Author> findAll();
    Optional<Author> save(String name, String surname, String nationality);
    Optional<Author> edit(Long id, String name, String surname, String nationality);
    void deleteById(Long id);
}
