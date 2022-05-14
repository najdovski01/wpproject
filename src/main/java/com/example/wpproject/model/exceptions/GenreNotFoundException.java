package com.example.wpproject.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class GenreNotFoundException extends RuntimeException {
    public GenreNotFoundException(Long id) {
        super(String.format("Genre with id: %d was not found", id));
    }
}
