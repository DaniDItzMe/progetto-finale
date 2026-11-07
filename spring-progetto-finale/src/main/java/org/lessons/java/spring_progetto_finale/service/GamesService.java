package org.lessons.java.spring_progetto_finale.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.lessons.java.spring_progetto_finale.model.Console;
import org.lessons.java.spring_progetto_finale.model.Game;
import org.lessons.java.spring_progetto_finale.repositories.ConsolesRepo;
import org.lessons.java.spring_progetto_finale.repositories.GamesRepo;
import org.lessons.java.spring_progetto_finale.repositories.GenresRepo;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Game> getAll() {

        List<Game> games = gameRepo.findAll();
        return games;

    }

    public Optional<Game> getById(Integer id) {

        Optional<Game> gameAttempt = gameRepo.findById(id);

        return gameAttempt;

    }

    public List<Game> getByName(String search) {

        List<Game> games = gameRepo.findByNameContaining(search);
        return games;

    }

    public List<Game> getByConsole(Console console) {

        List<Game> gamesByConsole = gameRepo.findByAvailableConsoles(console);

        return gamesByConsole;

    }

    public Game store(Game game, MultipartFile image) throws IOException {

        if (image != null && !image.isEmpty()) {
            game.setImage(image.getBytes());

        }

        return gameRepo.save(game);

    }

    public Game update(Game game, MultipartFile image) throws IOException {

        if (image != null && !image.isEmpty()) {

            game.setImage(image.getBytes());

        } else {

            Game originalGame = gameRepo.findById(game.getId()).get();
            game.setImage(originalGame.getImage());
        }

        return gameRepo.save(game);

    }

    public void deleteById(Integer id) {

        Game gameToDelete = gameRepo.findById(id).get();

        gameToDelete.getGenres().clear();
        gameToDelete.getAvailableConsoles().clear();

        gameRepo.delete(gameToDelete);

    }

}
