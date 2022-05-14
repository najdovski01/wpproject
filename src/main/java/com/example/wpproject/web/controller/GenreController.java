package com.example.wpproject.web.controller;

import com.example.wpproject.model.Genre;
import com.example.wpproject.service.GenreService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/genres")
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public String getGenrePage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        List<Genre> genres = this.genreService.findAll();
        model.addAttribute("genres", genres);
        model.addAttribute("bodyContent", "genres");
        return "master-template";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteGenre(@PathVariable Long id) {
        this.genreService.deleteById(id);
        return "redirect:/genres";
    }

    @GetMapping("/edit-form/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editAuthorPage(@PathVariable Long id, Model model) {
        if (this.genreService.findById(id).isPresent()) {
            Genre genre= this.genreService.findById(id).get();
            model.addAttribute("genre", genre);
            model.addAttribute("bodyContent", "add-genre");
            return "master-template";
        }
        return "redirect:/products?error=GenreNotFound";
    }

    @GetMapping("/add-form")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addAuthorPage(Model model) {
        model.addAttribute("bodyContent", "add-genre");
        return "master-template";
    }

    @PostMapping("/add")
    public String saveAuthor(
            @RequestParam(required = false) Long id,
            @RequestParam String name,
            @RequestParam String description) {
        if (id != null) {
            this.genreService.edit(id, name, description);
        } else {
            this.genreService.save(name, description);
        }
        return "redirect:/genres";
    }

}