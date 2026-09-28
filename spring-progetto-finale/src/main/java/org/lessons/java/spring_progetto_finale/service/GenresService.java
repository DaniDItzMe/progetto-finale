package org.lessons.java.spring_progetto_finale.service;

import java.util.List;
import java.util.Optional;

import org.lessons.java.spring_progetto_finale.model.Game;
import org.lessons.java.spring_progetto_finale.model.Genre;
import org.lessons.java.spring_progetto_finale.repositories.GenresRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GenresService {

    @Autowired
    private GenresRepo genreRepo;

    public ResponseEntity<List<Genre>> getAll() {

        List<Genre> genres = genreRepo.findAll();

        return new ResponseEntity<List<Genre>>(genres, HttpStatus.OK);
    }

    public ResponseEntity<Genre> getById(Integer id) {

        Optional<Genre> genre = genreRepo.findById(id);

        if (genre.isEmpty()) {

            return new ResponseEntity<Genre>(HttpStatus.NOT_FOUND);

        }

        return new ResponseEntity<Genre>(genre.get(), HttpStatus.OK);

    }

    public ResponseEntity<Genre> storeOrUpdate(Genre genre) {

        genreRepo.save(genre);

        return new ResponseEntity<Genre>(HttpStatus.OK);

    }

    public ResponseEntity<Genre> deleteById(Integer id) {

        Genre genreToDelete = genreRepo.findById(id).get();

        for (Game game : genreToDelete.getGames()) {

            game.getGenres().remove(genreToDelete);

        }

        genreRepo.delete(genreToDelete);

        return new ResponseEntity<Genre>(HttpStatus.OK);

    }

}
