package com.example.wpproject.repository;

import com.example.wpproject.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    //Author findByNameIsLike(String name);
}
