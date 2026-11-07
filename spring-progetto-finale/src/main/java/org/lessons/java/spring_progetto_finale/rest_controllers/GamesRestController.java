package org.lessons.java.spring_progetto_finale.rest_controllers;

import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.lessons.java.spring_progetto_finale.model.Game;
import org.lessons.java.spring_progetto_finale.repositories.GamesRepo;
import org.lessons.java.spring_progetto_finale.service.GamesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@CrossOrigin
@RequestMapping("/api/games")
public class GamesRestController {

    @Autowired
    private GamesService gameService;

    @GetMapping
    public ResponseEntity<List<Game>> index() {

        if (gameService.getAll().isEmpty()) {
            return new ResponseEntity<List<Game>>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<List<Game>>(gameService.getAll(), HttpStatus.OK);

    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Game> get(@PathVariable Integer id) {

        Optional<Game> game = gameService.getById(id);

        if (game.isEmpty()) {
            return new ResponseEntity<Game>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Game>(game.get(), HttpStatus.OK);

    }

    @GetMapping("/image/{id}")
    public void getImage(@PathVariable Integer id, HttpServletResponse response) throws IOException {

        Game game = gameService.getById(id).orElse(null);

        response.getOutputStream().write(game.getImage());

    }

}
