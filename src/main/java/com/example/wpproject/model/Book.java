package com.example.wpproject.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    private Author author;

    @Column(length = 2000)
    private String description;

    @ManyToOne
    private Genre genre;

    @ManyToOne
    private PublishHouse publishHouse;

    private double price;

    public Book() {
    }

    public Book(String name, Author author, Genre genre,  String description, double price, PublishHouse publishHouse) {
        this.name = name;
        this.author = author;
        this.description = description;
        this.genre = genre;
        this.price = price;
        this.publishHouse = publishHouse;
    }

    public PublishHouse getPublishHouse() {
        return publishHouse;
    }

    public void setPublishHouse(PublishHouse publishHouse) {
        this.publishHouse = publishHouse;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
