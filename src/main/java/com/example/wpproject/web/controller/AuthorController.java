package com.example.wpproject.web.controller;

import com.example.wpproject.model.Author;
import com.example.wpproject.model.Book;
import com.example.wpproject.model.Genre;
import com.example.wpproject.service.AuthorService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public String getAuthorPage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<Author> authors = this.authorService.findAll();
        model.addAttribute("authors", authors);
        model.addAttribute("bodyContent", "authors");
        return "master-template";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable Long id) {
        this.authorService.deleteById(id);
        return "redirect:/authors";
    }

    @GetMapping("/edit-form/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editAuthorPage(@PathVariable Long id, Model model) {
        if (this.authorService.findById(id).isPresent()) {
            Author author= this.authorService.findById(id).get();
            model.addAttribute("author", author);
            model.addAttribute("bodyContent", "add-author");
            return "master-template";
        }
        return "redirect:/products?error=GenreNotFound";
    }

    @GetMapping("/add-form")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addAuthorPage(Model model) {
        /**Change the name of the inputs in the templates for the request to be made and sent correctly/in a correct manner.
         * */
        model.addAttribute("bodyContent", "add-author");
        return "master-template";
    }

    @PostMapping("/add")
    public String saveAuthor(
            @RequestParam(required = false) Long id,
            @RequestParam String name,
            @RequestParam String surname,
            @RequestParam String nationality) {
        if (id != null) {
            this.authorService.edit(id, name, surname, nationality);
        } else {
            this.authorService.save(name, surname, nationality);
        }
        return "redirect:/authors";
    }

}
