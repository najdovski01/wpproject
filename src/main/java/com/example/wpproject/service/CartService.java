package com.example.wpproject.service;

import com.example.wpproject.model.Book;
import com.example.wpproject.model.Cart;

import java.util.List;

public interface CartService {
    List<Book> listAllBooksInCart(Long id);
    Cart getActiveCart(String username);
    Cart addBookToCart(String username, Long bookId);
    void deleteById(Long id);
}
