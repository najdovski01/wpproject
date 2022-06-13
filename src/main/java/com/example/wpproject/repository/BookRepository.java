package com.example.wpproject.repository;

import com.example.wpproject.model.Author;
import com.example.wpproject.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByName(String name);
    //void deleteByName(String name);
    List<Book> findByAuthor(Author author);
    Optional<Book> findById(Long id);
}
