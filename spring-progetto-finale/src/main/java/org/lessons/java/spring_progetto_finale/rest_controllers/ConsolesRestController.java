package org.lessons.java.spring_progetto_finale.rest_controllers;

import java.util.List;

import org.lessons.java.spring_progetto_finale.model.Console;
import org.lessons.java.spring_progetto_finale.model.Game;
import org.lessons.java.spring_progetto_finale.service.ConsolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/consoles")
public class ConsolesRestController {

    @Autowired
    private ConsolesService consoleService;

    @GetMapping
    public ResponseEntity<List<Console>> index() {

        if (consoleService.getAll().isEmpty()) {
            return new ResponseEntity<List<Console>>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<List<Console>>(consoleService.getAll(), HttpStatus.OK);

    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Console> getById(@PathVariable Integer id) {

        if (consoleService.getById(id).isEmpty()) {
            return new ResponseEntity<Console>(HttpStatus.NOT_FOUND);

        }
        return new ResponseEntity<Console>(consoleService.getById(id).get(), HttpStatus.OK);
    }

    @GetMapping("/getGames/{id}")
    public ResponseEntity<List<Game>> getGames(@PathVariable Integer id) {

        if (consoleService.getGames(id).isEmpty()) {
            return new ResponseEntity<List<Game>>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<List<Game>>(consoleService.getGames(id), HttpStatus.OK);

    }

}
