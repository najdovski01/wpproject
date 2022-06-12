package com.example.wpproject.service.impl;

import com.example.wpproject.model.Book;
import com.example.wpproject.model.Cart;
import com.example.wpproject.model.User;
import com.example.wpproject.model.enumerations.CartStatus;
import com.example.wpproject.model.exceptions.BookAlreadyInCartException;
import com.example.wpproject.model.exceptions.BookNotFoundException;
import com.example.wpproject.model.exceptions.CartNotFoundException;
import com.example.wpproject.model.exceptions.UserNotFoundException;
import com.example.wpproject.repository.BookRepository;
import com.example.wpproject.repository.CartRepository;
import com.example.wpproject.repository.UserRepository;
import com.example.wpproject.service.CartService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public CartServiceImpl(CartRepository cartRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> listAllBooksInCart(Long id) {
        if(!this.cartRepository.findById(id).isPresent())
            throw new CartNotFoundException(id);
        return this.cartRepository.findById(id).get().getBooks();
    }



    @Override
    public Cart getActiveCart(String username) {
        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        return this.cartRepository.findByUserAndStatus(user, CartStatus.CREATED)
                .orElseGet(() -> {
                    Cart cart = new Cart(user);
                    return this.cartRepository.save(cart);
                });
    }

    @Override
    public Cart addBookToCart(String username, Long bookId) {
        Cart cart = this.getActiveCart(username);
        Book book = this.bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
        if(cart.getBooks().stream().filter(b -> b.getId().equals(bookId)).collect(Collectors.toList()).size()>0){
            throw new BookAlreadyInCartException(bookId, username);
        }
        cart.getBooks().add(book);
        return this.cartRepository.save(cart);
    }
    @Override
    public void deleteById(Long id) {
        this.cartRepository.deleteById(id);
    }
}
