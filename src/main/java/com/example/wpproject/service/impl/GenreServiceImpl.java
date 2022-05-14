package com.example.wpproject.service.impl;

import com.example.wpproject.model.Genre;
import com.example.wpproject.repository.GenreRepository;
import com.example.wpproject.service.GenreService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public List<Genre> findAll() {
        return this.genreRepository.findAll();
    }

    @Override
    public Optional<Genre> findById(Long id) {
        return this.genreRepository.findById(id);
    }

    @Override
    public Optional<Genre> save(String name, String description) {
        Genre genre = new Genre(name, description);
        return Optional.of(this.genreRepository.save(genre));
    }

    @Override
    public Optional<Genre> edit(Long id, String name, String description) {
        if (name==null || name.isEmpty()) {
            throw new IllegalArgumentException();
        }
        Genre genre = this.genreRepository.getById(id);
        genre.setName(name);
        genre.setDescription(description);
        return Optional.of(this.genreRepository.save(genre));

    }

    @Override
    public void delete(String name) {
        this.genreRepository.deleteByName(name);
    }

    @Override
    public void deleteById(Long id) {
        this.genreRepository.deleteById(id);
    }

    @Override
    public List<Genre> searchGenre(String searchText) {
        return this.genreRepository.findAllByNameLike(searchText);
    }
}
