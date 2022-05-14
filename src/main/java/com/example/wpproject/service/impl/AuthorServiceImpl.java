package com.example.wpproject.service.impl;

import com.example.wpproject.model.Author;
import com.example.wpproject.model.exceptions.AuthorNotFoundException;
import com.example.wpproject.repository.AuthorRepository;
import com.example.wpproject.service.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Optional<Author> findById(Long id) {
        return this.authorRepository.findById(id);
    }

    @Override
    public List<Author> findAll() {
        return this.authorRepository.findAll();
    }

    @Override
    public Optional<Author> save(String name, String surname, String nationality) {
        Author author = new Author(name, surname, nationality);
        return Optional.of(this.authorRepository.save(author));
    }

    @Override
    public Optional<Author> edit(Long id, String name, String surname, String nationality) {
        Author author = this.findById(id).orElseThrow(() -> new AuthorNotFoundException(id));
        author.setName(name);
        author.setSurname(surname);
        author.setNationality(nationality);
        return Optional.of(this.authorRepository.save(author));
    }

    @Override
    public void deleteById(Long id) {
        this.authorRepository.deleteById(id);
    }
}
