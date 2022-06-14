package com.example.wpproject.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class PublishHouseNotFoundException extends RuntimeException{

    public PublishHouseNotFoundException(Long id) {
        super(String.format("Publish house with id: %d was not found", id));
    }
}
