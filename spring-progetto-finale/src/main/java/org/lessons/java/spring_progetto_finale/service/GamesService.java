package org.lessons.java.spring_progetto_finale.service;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class GamesService {

    @Autowired
    private GamesRepo gameRepo;

    @Autowired
    private GenresRepo genreRepo;

    @Autowired
    private ConsolesRepo consoleRepo;

    public ResponseEntity<List<Game>> getAll() {

        List<Game> games = gameRepo.findAll();

        if (games.isEmpty()) {

            return new ResponseEntity<List<Game>>(HttpStatus.NOT_FOUND);

        }

        return new ResponseEntity<List<Game>>(games, HttpStatus.OK);

    }

    public ResponseEntity<Game> findById(Integer id) {

        Optional<Game> gameAttempt = gameRepo.findById(id);

        if (gameAttempt.isEmpty()) {

            return new ResponseEntity<Game>(HttpStatus.NOT_FOUND);

        }

        return new ResponseEntity<Game>(gameAttempt.get(), HttpStatus.OK);

    }

    public ResponseEntity<List<Game>> findByName(String search) {

        List<Game> games = gameRepo.findByNameContaining(search);

        if (games.isEmpty()) {

            return new ResponseEntity<List<Game>>(HttpStatus.NOT_FOUND);

        }

        return new ResponseEntity<List<Game>>(games, HttpStatus.OK);

    }

    public ResponseEntity<List<Game>> findByConsole(Console console) {

        List<Game> gamesByConsole = gameRepo.findByAvailableConsoles(console);

        if (gamesByConsole.isEmpty()) {
            return new ResponseEntity<List<Game>>(HttpStatus.NOT_FOUND);

        }

        return new ResponseEntity<List<Game>>(gamesByConsole, HttpStatus.OK);

    }

    public ResponseEntity<Game> store(Game game, MultipartFile image) throws IOException {

        List<Genre> genres = genreRepo.findAllById(game.getGenres()
                .stream()
                .map(genre -> genre.getId())
                .toList());

        List<Console> consoles = consoleRepo.findAllById(game.getAvailableConsoles()
                .stream()
                .map(console -> console.getId())
                .toList());

        game.setGenres(genres);
        game.setAvailableConsoles(consoles);

        if (image != null && !image.isEmpty()) {
            System.out.println("IMAGE IS NULL OR EMPTY");
            game.setImage(image.getBytes());

        }

        gameRepo.save(game);

        return new ResponseEntity<Game>(HttpStatus.CREATED);

    }

    public ResponseEntity<Game> update(Game game, MultipartFile image) throws IOException {

        List<Genre> genres = genreRepo.findAllById(game.getGenres()
                .stream()
                .map(genre -> genre.getId())
                .toList());

        List<Console> consoles = consoleRepo.findAllById(game.getAvailableConsoles()
                .stream()
                .map(console -> console.getId())
                .toList());

        game.setGenres(genres);
        game.setAvailableConsoles(consoles);

        if (image != null) {

            game.setImage(image.getBytes());

        } else {

            Game originalGame = gameRepo.findById(game.getId()).get();
            game.setImage(originalGame.getImage());
        }
        gameRepo.save(game);

        return new ResponseEntity<Game>(HttpStatus.OK);

    }

    public ResponseEntity<Game> deleteById(Integer id) {

        Game gameToDelete = gameRepo.findById(id).get();

        gameToDelete.getGenres().clear();
        gameToDelete.getAvailableConsoles().clear();

        gameRepo.delete(gameToDelete);

        return new ResponseEntity<Game>(HttpStatus.OK);

    }

}
