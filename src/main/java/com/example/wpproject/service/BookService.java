package com.example.wpproject.service;

import com.example.wpproject.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> findAll();
    Optional<Book> findById(Long id);
    Optional<Book> findByName(String name);
    Optional<Book> save(String name, Long authorId, Long genreId, String description, Double price);
    Optional<Book> edit(Long id, String name, Long authorId, Long genreId, String description, Double price);
    void deleteById(Long id);
}
