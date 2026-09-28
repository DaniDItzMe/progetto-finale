package org.lessons.java.spring_progetto_finale.controllers;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import org.lessons.java.spring_progetto_finale.model.Game;
import org.lessons.java.spring_progetto_finale.model.Genre;
import org.lessons.java.spring_progetto_finale.repositories.GenresRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/genres")
public class GenresController {

    @Autowired
    private GenresRepo genreRepo;

    @GetMapping
    public String index(Model model) {

        List<Genre> genres = genreRepo.findAll();

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

        genreRepo.save(genre);

        return "redirect:/genres";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {

        Genre genre = genreRepo.findById(id).orElse(null);

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

        genreRepo.save(genre);

        return "redirect:/genres";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {

        Genre genreToDelete = genreRepo.findById(id).get();

        for (Game game : genreToDelete.getGames()) {

            game.getGenres().remove(genreToDelete);

        }

        genreRepo.delete(genreToDelete);

        return "redirect:/genres";
    }

}
