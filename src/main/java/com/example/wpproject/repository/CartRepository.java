package com.example.wpproject.repository;

import com.example.wpproject.model.Cart;
import com.example.wpproject.model.User;
import com.example.wpproject.model.enumerations.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserAndStatus(User user, CartStatus status);
    public void deleteById(Long id);
}
