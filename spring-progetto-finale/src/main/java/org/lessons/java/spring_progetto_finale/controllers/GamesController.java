package org.lessons.java.spring_progetto_finale.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.lessons.java.spring_progetto_finale.model.Console;
import org.lessons.java.spring_progetto_finale.model.Game;
import org.lessons.java.spring_progetto_finale.model.Genre;
import org.lessons.java.spring_progetto_finale.repositories.ConsolesRepo;
import org.lessons.java.spring_progetto_finale.repositories.GamesRepo;
import org.lessons.java.spring_progetto_finale.repositories.GenresRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/")
public class GamesController {

    @Autowired
    private GamesRepo gameRepo;

    @Autowired
    private GenresRepo genreRepo;

    @Autowired
    private ConsolesRepo consoleRepo;

    @GetMapping
    public String index(Model model) {

        List<Game> games = gameRepo.findAll();

        model.addAttribute("games", games);

        return "games/index";

    }

    @GetMapping("/show/{id}")
    public String show(@PathVariable Integer id, Model model) {

        Game game = gameRepo.findById(id).orElse(null);

        model.addAttribute("game", game);

        return "games/show";
    }

    @GetMapping("/create")
    public String create(Model model) {

        Game game = new Game();
        List<Genre> genresList = genreRepo.findAll();
        List<Console> consolesList = consoleRepo.findAll();

        model.addAttribute("game", game);
        model.addAttribute("genresList", genresList);
        model.addAttribute("consolesList", consolesList);
        model.addAttribute("edit", false);

        return "games/create-edit";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute Game game, BindingResult bindingResult, Model model,
            @RequestParam("coverImage") MultipartFile image) throws IOException {

        if (bindingResult.hasErrors()) {

            List<Genre> genresList = genreRepo.findAll();
            List<Console> consolesList = consoleRepo.findAll();
            model.addAttribute("genresList", genresList);
            model.addAttribute("consolesList", consolesList);
            model.addAttribute("edit", false);
            System.out.println("Si è verificato un errore");
            bindingResult.getFieldErrors().forEach(error -> System.out.println(
                    error.getField() + " -> " + error.getDefaultMessage()));
            return "games/create-edit";

        }

        if (!image.isEmpty()) {
            game.setImage(image.getBytes());
        }

        gameRepo.save(game);

        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {

        Game game = gameRepo.findById(id).orElse(null);
        List<Genre> genresList = genreRepo.findAll();
        List<Console> consolesList = consoleRepo.findAll();
        model.addAttribute("game", game);
        model.addAttribute("genresList", genresList);
        model.addAttribute("consolesList", consolesList);
        model.addAttribute("edit", true);

        return "games/create-edit";

    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute Game game, BindingResult bindingResult,
            @RequestParam("coverImage") MultipartFile image, @PathVariable Integer id, Model model) throws IOException {

        if (bindingResult.hasErrors()) {

            List<Genre> genresList = genreRepo.findAll();
            List<Console> consolesList = consoleRepo.findAll();
            model.addAttribute("genresList", genresList);
            model.addAttribute("consolesList", consolesList);
            model.addAttribute("edit", true);

            bindingResult.getFieldErrors().forEach(error -> System.out.println(
                    error.getField() + " -> " + error.getDefaultMessage()));
            return "games/create-edit";

        }
        if (!image.isEmpty() && image != null) {

            game.setImage(image.getBytes());

        } else {

            Game originalGame = gameRepo.findById(id).get();
            game.setImage(originalGame.getImage());
        }
        gameRepo.save(game);

        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {

        Game gameToDelete = gameRepo.findById(id).get();

        gameToDelete.getAvailableConsoles().clear();
        gameToDelete.getGenres().clear();

        gameRepo.deleteById(id);

        return "redirect:/";
    }

    @GetMapping("/search")
    public String search(@RequestParam String search, Model model) {

        List<Game> searchedGame = gameRepo.findByNameContaining(search);

        model.addAttribute("games", searchedGame);
        model.addAttribute("searchParam", search);

        return "games/index";

    }

    @GetMapping("/image/{id}")
    public void getImage(@PathVariable Integer id, HttpServletResponse response) throws IOException {

        Game game = gameRepo.findById(id).orElse(null);

        response.getOutputStream().write(game.getImage());
    }

}
