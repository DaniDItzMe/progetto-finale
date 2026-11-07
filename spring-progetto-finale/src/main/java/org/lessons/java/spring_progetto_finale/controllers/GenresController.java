package org.lessons.java.spring_progetto_finale.controllers;

import java.util.List;

import org.lessons.java.spring_progetto_finale.model.Genre;
import org.lessons.java.spring_progetto_finale.service.GenresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/genres")
public class GenresController {

    @Autowired
    private GenresService genresService;

    @GetMapping
    public String index(Model model) {

        List<Genre> genres = genresService.getAll();

        model.addAttribute("genres", genres);

        return "genres/index";

    }

    @GetMapping("/create")
    public String create(Model model) {

        Genre genre = new Genre();

        model.addAttribute("genre", genre);
        model.addAttribute("edit", false);

        return "genres/create-edit";

    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute Genre genre, BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("edit", false);
            return "genres/create-edit";

        }

        genresService.storeOrUpdate(genre);

        return "redirect:/genres";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {

        Genre genre = genresService.getById(id).orElse(null);

        model.addAttribute("genre", genre);
        model.addAttribute("edit", true);

        return "genres/create-edit";

    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute Genre genre, BindingResult bindingResult, @PathVariable Integer id,
            Model model) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("edit", true);

            return "genres/create-edit";

        }

        genresService.storeOrUpdate(genre);

        return "redirect:/genres";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {

        genresService.deleteById(id);

        return "redirect:/genres";
    }

}
