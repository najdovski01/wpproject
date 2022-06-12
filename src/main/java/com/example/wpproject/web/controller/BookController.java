package com.example.wpproject.web.controller;

import com.example.wpproject.model.Author;
import com.example.wpproject.model.Book;
import com.example.wpproject.model.Genre;
import com.example.wpproject.model.PublishHouse;
import com.example.wpproject.service.AuthorService;
import com.example.wpproject.service.BookService;
import com.example.wpproject.service.GenreService;
import com.example.wpproject.service.PublishHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final GenreService genreService;
    private final AuthorService authorService;
    @Autowired
    private final PublishHouseService publishHouseService;

    public BookController(BookService bookService, GenreService genreService, AuthorService authorService, PublishHouseService publishHouseService) {
        this.bookService = bookService;
        this.genreService = genreService;
        this.authorService = authorService;
        this.publishHouseService = publishHouseService;
    }

    @GetMapping
    public String getBookPage(@RequestParam(required = false) String error,
                              Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<Book> books = this.bookService.findAll();

        /*
        if(name==null || name.isEmpty()){
             books = this.bookService.findAll();
        }
        else {
            books = this.bookService.filter(name);
        }

         */
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
            List<PublishHouse> publishHouses = this.publishHouseService.findAll();
            model.addAttribute("authors", authors);
            model.addAttribute("genres", genres);
            model.addAttribute("publishHouses", publishHouses);
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
        List<PublishHouse> publishHouses = this.publishHouseService.findAll();
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);
        model.addAttribute("publishHouses", publishHouses);
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
            @RequestParam Double price,
            @RequestParam Long publishHouse
            ) {
        if (id != null) {
            this.bookService.edit(id, name, author, genre, description, price, publishHouse);
        } else {
            this.bookService.save(name, author, genre, description, price, publishHouse);
        }
        return "redirect:/books";
    }

    @GetMapping("/{id}/pdf/generate")
    public void generatePDF(HttpServletResponse response, @PathVariable Long id) throws IOException {
        response.setContentType("application/pdf");



        String book = this.bookService.findById(id).get().getName();

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Info_" + book + ".pdf";
        response.setHeader(headerKey, headerValue);

        this.bookService.export(response, id);

    }

}
