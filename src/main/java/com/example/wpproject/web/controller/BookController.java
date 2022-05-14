package com.example.wpproject.web.controller;

import com.example.wpproject.model.Author;
import com.example.wpproject.model.Book;
import com.example.wpproject.model.Genre;
import com.example.wpproject.model.exceptions.BookNotFoundException;
import com.example.wpproject.model.exceptions.InvalidUserCredentialsException;
import com.example.wpproject.service.AuthorService;
import com.example.wpproject.service.BookService;
import com.example.wpproject.service.GenreService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final GenreService genreService;
    private final AuthorService authorService;

    public BookController(BookService bookService, GenreService genreService, AuthorService authorService) {
        this.bookService = bookService;
        this.genreService = genreService;
        this.authorService = authorService;
    }

    @GetMapping
    public String getBookPage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<Book> books = this.bookService.findAll();
        model.addAttribute("books", books);
        model.addAttribute("bodyContent", "books");
        return "master-template";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        this.bookService.deleteById(id);
        return "redirect:/books";
    }

    @GetMapping("/edit-form/{id}")
    public String editBookPage(@PathVariable Long id, Model model) {
        if (this.bookService.findById(id).isPresent()) {
            Book book= this.bookService.findById(id).get();
            List<Author> authors = this.authorService.findAll();
            List<Genre> genres = this.genreService.findAll();
            model.addAttribute("authors", authors);
            model.addAttribute("genres", genres);
            model.addAttribute("book", book);
            model.addAttribute("bodyContent", "add-book");
            return "master-template";
        }
        return "redirect:/products?error=BookNotFound";
    }

    @GetMapping("/add-form")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addBookPage(Model model) {
        List<Author> authors = this.authorService.findAll();
        List<Genre> genres = this.genreService.findAll();
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);
        model.addAttribute("bodyContent", "add-book");
        return "master-template";
    }

    @PostMapping("/add")
    public String saveBook(
            @RequestParam(required = false) Long id,
            @RequestParam String name,
            @RequestParam Long author,
            @RequestParam String description,
            @RequestParam Long genre,
            @RequestParam Double price
            ) {
        if (id != null) {
            this.bookService.edit(id, name, author, genre, description, price);
        } else {
            this.bookService.save(name, author, genre, description, price);
        }
        return "redirect:/books";
    }

}
