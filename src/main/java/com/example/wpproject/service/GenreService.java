package com.example.wpproject.service;

import com.example.wpproject.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {
    List<Genre> findAll();
    Optional<Genre> findById(Long id);
    Optional<Genre> save(String name, String description);
    Optional<Genre> edit(Long id, String name, String description);
    void delete(String name);
    void deleteById(Long id);
    List<Genre> searchGenre(String searchText);
}
