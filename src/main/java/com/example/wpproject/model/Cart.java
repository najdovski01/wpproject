package com.example.wpproject.model;

import com.example.wpproject.model.enumerations.CartStatus;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateCreated;

    @ManyToOne
    private User user;

    @ManyToMany
    private List<Book> books;

    @Enumerated(EnumType.STRING)
    private CartStatus status;

    public Cart() {
    }

    public Cart (User user) {
        this.dateCreated = LocalDateTime.now();
        this.user = user;
        this.books = new ArrayList<>();
        this.status = CartStatus.CREATED;
    }
}
