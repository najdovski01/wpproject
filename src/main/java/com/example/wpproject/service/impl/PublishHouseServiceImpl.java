package com.example.wpproject.service.impl;

import com.example.wpproject.model.PublishHouse;
import com.example.wpproject.model.exceptions.PublishHouseNotFoundException;
import com.example.wpproject.repository.PublishHouseRepository;
import com.example.wpproject.service.PublishHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PublishHouseServiceImpl implements PublishHouseService {
    @Autowired
    private PublishHouseRepository publishHouseRepository;

    @Override
    public Optional<PublishHouse> findById(Long id) {
        return this.publishHouseRepository.findById(id);
    }

    @Override
    public List<PublishHouse> findAll() {
        return this.publishHouseRepository.findAll();
    }

    @Override
    public Optional<PublishHouse> save(String name, String address, String country) {
       PublishHouse publishHouse = new PublishHouse(name, address, country);
        return Optional.of(this.publishHouseRepository.save(publishHouse));
    }

    @Override
    public Optional<PublishHouse> edit(Long id, String name, String address, String country) {
      PublishHouse publishHouse = this.findById(id).orElseThrow(() -> new PublishHouseNotFoundException(id));
      publishHouse.setName(name);
      publishHouse.setAddress(address);
      publishHouse.setCountry(country);
        return Optional.of(this.publishHouseRepository.save(publishHouse));
    }

    @Override
    public void deleteById(Long id) {
         this.publishHouseRepository.deleteById(id);
    }
}
