package org.lessons.java.spring_progetto_finale.controllers;

import java.util.ArrayList;
import java.util.List;

import org.lessons.java.spring_progetto_finale.model.Console;
import org.lessons.java.spring_progetto_finale.model.Game;
import org.lessons.java.spring_progetto_finale.repositories.ConsolesRepo;
import org.lessons.java.spring_progetto_finale.repositories.GamesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/consoles")
public class ConsolesController {

    @Autowired
    private ConsolesRepo consoleRepo;

    @Autowired
    private GamesRepo gameRepo;

    @GetMapping
    public String index(Model model) {

        List<Console> consoles = consoleRepo.findAll();
        model.addAttribute("consoles", consoles);
        return "consoles/index";
    }

    @GetMapping("/show/{id}")
    public String show(@PathVariable Integer id, Model model) {

        Console console = consoleRepo.findById(id).get();
        List<Game> gamesByConsole = gameRepo.findByAvailableConsoles(console);

        model.addAttribute("gamesByConsole", gamesByConsole);
        model.addAttribute("console", console);

        return "consoles/show";
    }

    @GetMapping("/create")
    public String create(Model model) {

        Console console = new Console();

        model.addAttribute("console", console);
        model.addAttribute("edit", false);

        return "consoles/create-edit";

    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute Console console, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("edit", false);

            return "consoles/create-edit";

        }

        consoleRepo.save(console);

        return "redirect:/consoles";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {

        Console console = consoleRepo.findById(id).orElse(null);

        model.addAttribute("console", console);
        model.addAttribute("edit", true);

        return "consoles/create-edit";

    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute Console console, BindingResult bindingResult, @PathVariable Integer id,
            Model model) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("edit", true);
            bindingResult.getFieldErrors()
                    .forEach(err -> System.out.println(err.getField() + "->" + err.getDefaultMessage()));
            return "consoles/create-edit";

        }

        consoleRepo.save(console);

        return "redirect:/consoles";

    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {

        Console consoleToDelete = consoleRepo.findById(id).get();

        for (Game game : consoleToDelete.getGames()) {

            game.getAvailableConsoles().remove(consoleToDelete);

        }

        consoleRepo.delete(consoleToDelete);

        return "redirect:/consoles";
    }

}
