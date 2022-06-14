package com.example.wpproject.web.controller;

import com.example.wpproject.model.Author;
import com.example.wpproject.model.PublishHouse;
import com.example.wpproject.service.PublishHouseService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/publishHouses")
public class PublishHouseController {

    private final PublishHouseService publishHouseService;

    public PublishHouseController(PublishHouseService publishHouseService) {
        this.publishHouseService = publishHouseService;
    }

    @GetMapping
    public String getPublishHousePage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<PublishHouse> publishHouses = this.publishHouseService.findAll();
        model.addAttribute("publishHouses", publishHouses);
        model.addAttribute("bodyContent", "publishHouses");
        return "master-template";
    }

    @DeleteMapping("/delete/{id}")
    public String deletePublishHouse(@PathVariable Long id) {
        this.publishHouseService.deleteById(id);
        return "redirect:/publishHouses";
    }

    @GetMapping("/edit-form/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editPublishHousePage(@PathVariable Long id, Model model) {
        if (this.publishHouseService.findById(id).isPresent()) {
            PublishHouse publishHouse= this.publishHouseService.findById(id).get();
            model.addAttribute("publishHouse", publishHouse);
            model.addAttribute("bodyContent", "add-publishHouse");
            return "master-template";
        }
        return "redirect:/products?error=GenreNotFound";
    }

    @GetMapping("/add-form")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addPublishHousePage(Model model) {
        /**Change the name of the inputs in the templates for the request to be made and sent correctly/in a correct manner.
         * */
        model.addAttribute("bodyContent", "add-publishHouse");
        return "master-template";
    }

    @PostMapping("/add")
    public String saveAuthor(
            @RequestParam(required = false) Long id,
            @RequestParam String name,
            @RequestParam String address,
            @RequestParam String country) {
        if (id != null) {
            this.publishHouseService.edit(id, name, address, country);
        } else {
            this.publishHouseService.save(name, address, country);
        }
        return "redirect:/publishHouses";
    }
}
