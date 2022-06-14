package com.example.wpproject.repository;

import com.example.wpproject.model.PublishHouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PublishHouseRepository extends JpaRepository<PublishHouse, Long> {
}
