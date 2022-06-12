package com.example.wpproject.repository;

import com.example.wpproject.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByName(String name);
    //void deleteByName(String name);
    //List<Book> findByAuthor(Author author);
}
