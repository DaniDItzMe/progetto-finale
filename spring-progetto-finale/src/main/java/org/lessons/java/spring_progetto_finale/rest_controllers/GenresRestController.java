package org.lessons.java.spring_progetto_finale.rest_controllers;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.lessons.java.spring_progetto_finale.model.Genre;
import org.lessons.java.spring_progetto_finale.service.GenresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@CrossOrigin
@RequestMapping("/api/genres")
public class GenresRestController {

    @Autowired
    private GenresService genresService;

    @GetMapping
    public ResponseEntity<List<Genre>> index() {

        if (genresService.getAll().isEmpty()) {
            return new ResponseEntity<List<Genre>>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<List<Genre>>(genresService.getAll(), HttpStatus.OK);

    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Genre> get(@PathVariable Integer id) {

        if (genresService.getById(id).isEmpty()) {
            return new ResponseEntity<Genre>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Genre>(genresService.getById(id).get(), HttpStatus.OK);

    }

}
