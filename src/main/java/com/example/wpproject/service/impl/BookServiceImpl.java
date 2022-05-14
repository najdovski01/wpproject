package com.example.wpproject.service.impl;

import com.example.wpproject.model.Author;
import com.example.wpproject.model.Book;
import com.example.wpproject.model.Genre;
import com.example.wpproject.model.exceptions.AuthorNotFoundException;
import com.example.wpproject.model.exceptions.BookNotFoundException;
import com.example.wpproject.model.exceptions.GenreNotFoundException;
import com.example.wpproject.repository.AuthorRepository;
import com.example.wpproject.repository.BookRepository;
import com.example.wpproject.repository.GenreRepository;
import com.example.wpproject.service.BookService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    @Override
    public List<Book> findAll() {
        return this.bookRepository.findAll();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return this.bookRepository.findById(id);
    }

    @Override
    public Optional<Book> findByName(String name) {
        return this.bookRepository.findByName(name);
    }

    @Override
    public Optional<Book> save(String name, Long authorId, Long genreId, String description, Double price) {
        Author author = this.authorRepository.findById(authorId).orElseThrow(() -> new AuthorNotFoundException(authorId));
        Genre genre = this.genreRepository.findById(genreId).orElseThrow(() -> new GenreNotFoundException(genreId));
        Book book = new Book(name, author, genre, description, price);
        return Optional.of(this.bookRepository.save(book));
    }

    @Override
    @Transactional
    public Optional<Book> edit(Long id, String name, Long authorId, Long genreId, String description, Double price) {
        Author author = this.authorRepository.findById(authorId).orElseThrow(() -> new AuthorNotFoundException(authorId));
        Genre genre = this.genreRepository.findById(genreId).orElseThrow(() -> new GenreNotFoundException(genreId));
        Book book = this.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        book.setName(name);
        book.setAuthor(author);
        book.setGenre(genre);
        book.setDescription(description);
        book.setPrice(price);
        return Optional.of(this.bookRepository.save(book));
    }

    @Override
    public void deleteById(Long id) {
        this.bookRepository.deleteById(id);
    }
}
