package org.lessons.java.spring_progetto_finale.rest_controllers;

import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lessons.java.spring_progetto_finale.model.Genre;
import org.lessons.java.spring_progetto_finale.service.GenresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/genres")
public class GenresRestController {

    @Autowired
    private GenresService genresService;

    @GetMapping
    public ResponseEntity<List<Genre>> index() {

        return genresService.getAll();

    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Genre> get(@PathVariable Integer id) {

        return genresService.getById(id);

    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> edit(@Valid @RequestBody Genre genre, BindingResult bindingResult,
            @PathVariable Integer id) {

        if (bindingResult.hasErrors()) {

            Map<String, String> errors = new HashMap<>();

            bindingResult.getFieldErrors().forEach(err -> {
                errors.put(err.getField(), err.getDefaultMessage());
            });

            return ResponseEntity.badRequest().body(errors);

        }

        return genresService.storeOrUpdate(genre);
    }

    @PostMapping("/create")
    public ResponseEntity<?> store(@Valid @RequestBody Genre genre, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {

            Map<String, String> errors = new HashMap<>();

            bindingResult.getFieldErrors().forEach(err -> {
                errors.put(err.getField(), err.getDefaultMessage());
            });

            return ResponseEntity.badRequest().body(errors);

        }

        return genresService.storeOrUpdate(genre);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Genre> delete(@PathVariable Integer id) {

        return genresService.deleteById(id);

    }

}
