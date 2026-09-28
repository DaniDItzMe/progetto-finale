package org.lessons.java.spring_progetto_finale.service;

import java.lang.classfile.ClassFile.Option;
import java.util.List;
import java.util.Optional;

import org.lessons.java.spring_progetto_finale.model.Console;
import org.lessons.java.spring_progetto_finale.model.Game;
import org.lessons.java.spring_progetto_finale.model.Genre;
import org.lessons.java.spring_progetto_finale.repositories.ConsolesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ConsolesService {

    @Autowired
    private ConsolesRepo consoleRepo;

    public ResponseEntity<List<Console>> getAll() {

        List<Console> consoles = consoleRepo.findAll();

        return new ResponseEntity<List<Console>>(consoles, HttpStatus.OK);

    }

    public ResponseEntity<Console> getById(Integer id) {

        Optional<Console> console = consoleRepo.findById(id);

        if (console.isEmpty()) {

            return new ResponseEntity<Console>(HttpStatus.NOT_FOUND);

        }

        return new ResponseEntity<Console>(console.get(), HttpStatus.OK);
    }

    public ResponseEntity<Console> storeOrUpdate(Console console) {

        consoleRepo.save(console);

        return new ResponseEntity<Console>(HttpStatus.OK);

    }

    public ResponseEntity<List<Game>> getGames(Integer id) {

        Optional<Console> console = consoleRepo.findById(id);

        if (console.isEmpty()) {

            return new ResponseEntity<List<Game>>(HttpStatus.NOT_FOUND);

        }

        return new ResponseEntity<List<Game>>(console.get().getGames(), HttpStatus.OK);

    }

    public ResponseEntity<Console> deleteById(Integer id) {

        Console consoleToDelete = consoleRepo.findById(id).get();

        for (Game game : consoleToDelete.getGames()) {

            game.getAvailableConsoles().remove(consoleToDelete);

        }

        consoleRepo.delete(consoleToDelete);

        return new ResponseEntity<Console>(HttpStatus.OK);

    }

}
