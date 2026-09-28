package org.lessons.java.spring_progetto_finale.rest_controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lessons.java.spring_progetto_finale.model.Console;
import org.lessons.java.spring_progetto_finale.model.Game;
import org.lessons.java.spring_progetto_finale.service.ConsolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/consoles")
public class ConsolesRestController {

    @Autowired
    private ConsolesService consoleService;

    @GetMapping
    public ResponseEntity<List<Console>> index() {

        return consoleService.getAll();

    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Console> getById(@PathVariable Integer id) {

        System.out.println("Backend consoles");

        return consoleService.getById(id);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> edit(@Valid @RequestBody Console console, BindingResult bindingResult,
            @PathVariable Integer id) {

        System.out.println(console.getName());
        System.out.println(console.getDescription());
        System.out.println(console.getPrice());

        if (bindingResult.hasErrors()) {

            Map<String, String> errors = new HashMap<>();

            bindingResult.getFieldErrors().forEach(err -> {
                errors.put(err.getField(), err.getDefaultMessage());
            });

            return ResponseEntity.badRequest().body(errors);

        }

        return consoleService.storeOrUpdate(console);

    }

    @PostMapping("/create")
    public ResponseEntity<?> store(@Valid @RequestBody Console console, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {

            Map<String, String> errors = new HashMap<>();

            bindingResult.getFieldErrors().forEach(err -> {
                errors.put(err.getField(), err.getDefaultMessage());
            });

            return ResponseEntity.badRequest().body(errors);

        }

        return consoleService.storeOrUpdate(console);
    }

    @GetMapping("/getGames/{id}")
    public ResponseEntity<List<Game>> getGames(@PathVariable Integer id) {

        return consoleService.getGames(id);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Console> delete(@PathVariable Integer id) {

        return consoleService.deleteById(id);

    }

}
