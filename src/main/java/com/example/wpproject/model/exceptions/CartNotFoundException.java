package com.example.wpproject.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CartNotFoundException extends RuntimeException{

    public CartNotFoundException(Long id) {
        super(String.format("Shopping cart with id: %d was not found", id));
    }
}

