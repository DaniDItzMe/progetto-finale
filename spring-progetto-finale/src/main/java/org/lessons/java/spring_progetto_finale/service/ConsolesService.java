package org.lessons.java.spring_progetto_finale.service;

import java.util.List;
import java.util.Optional;

import org.lessons.java.spring_progetto_finale.model.Console;
import org.lessons.java.spring_progetto_finale.model.Game;
import org.lessons.java.spring_progetto_finale.repositories.ConsolesRepo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class ConsolesService {

    @Autowired
    private ConsolesRepo consoleRepo;

    public List<Console> getAll() {

        List<Console> consoles = consoleRepo.findAll();

        return consoles;

    }

    public Optional<Console> getById(Integer id) {

        Optional<Console> console = consoleRepo.findById(id);

        return console;
    }

    public Console storeOrUpdate(Console console) {

        return consoleRepo.save(console);

    }

    public List<Game> getGames(Integer id) {

        Optional<Console> console = consoleRepo.findById(id);
        List<Game> games = null;
        if (!console.isEmpty()) {

            games = console.get().getGames();

        }

        return games;

    }

    public void deleteById(Integer id) {

        Console consoleToDelete = consoleRepo.findById(id).get();

        for (Game game : consoleToDelete.getGames()) {

            game.getAvailableConsoles().remove(consoleToDelete);

        }

        consoleRepo.delete(consoleToDelete);

    }

}
