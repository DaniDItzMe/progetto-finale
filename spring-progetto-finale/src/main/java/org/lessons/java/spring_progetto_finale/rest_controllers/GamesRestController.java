package org.lessons.java.spring_progetto_finale.rest_controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lessons.java.spring_progetto_finale.model.Game;
import org.lessons.java.spring_progetto_finale.repositories.GamesRepo;
import org.lessons.java.spring_progetto_finale.service.GamesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/games")
public class GamesRestController {

    @Autowired
    private GamesService gameService;

    @Autowired
    private GamesRepo gameRepo;

    @GetMapping
    public ResponseEntity<List<Game>> index() {

        return gameService.getAll();

    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Game> get(@PathVariable Integer id) {

        return gameService.findById(id);

    }

    @PostMapping("/create")
    public ResponseEntity<?> store(@Valid @RequestPart("game") Game game, BindingResult bindingResult,
            @RequestPart(value = "coverImage", required = false) MultipartFile image) throws IOException {

        System.out.println(image);

        if (bindingResult.hasErrors()) {

            Map<String, String> errors = new HashMap<>();

            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

            return ResponseEntity.badRequest().body(errors);

        }

        return gameService.store(game, image);

    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> update(@Valid @RequestPart("game") Game game, BindingResult bindingResult,
            @RequestParam(value = "coverImage", required = false) MultipartFile image, @PathVariable Integer id,
            Authentication authentication)
            throws IOException {

        if (bindingResult.hasErrors()) {

            Map<String, String> errors = new HashMap<>();

            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

            return ResponseEntity.badRequest().body(errors);

        }

        return gameService.update(game, image);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Game> delete(@PathVariable Integer id) {

        return gameService.deleteById(id);

    }

    @GetMapping("/image/{id}")
    public void getImage(@PathVariable Integer id, HttpServletResponse response) throws IOException {

        Game game = gameRepo.findById(id).orElse(null);

        response.getOutputStream().write(game.getImage());

    }

}
