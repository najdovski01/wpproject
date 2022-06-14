package com.example.wpproject.service;

import com.example.wpproject.model.Author;
import com.example.wpproject.model.PublishHouse;

import java.util.List;
import java.util.Optional;

public interface PublishHouseService {
    Optional<PublishHouse> findById(Long id);
    List<PublishHouse> findAll();
    Optional<PublishHouse> save(String name, String address, String country);
    Optional<PublishHouse> edit(Long id, String name, String address, String country);
    void deleteById(Long id);
}
