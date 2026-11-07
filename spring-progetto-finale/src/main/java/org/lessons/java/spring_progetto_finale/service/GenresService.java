package org.lessons.java.spring_progetto_finale.service;

import java.util.List;
import java.util.Optional;

import org.lessons.java.spring_progetto_finale.model.Game;
import org.lessons.java.spring_progetto_finale.model.Genre;
import org.lessons.java.spring_progetto_finale.repositories.GenresRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenresService {

    @Autowired
    private GenresRepo genreRepo;

    public List<Genre> getAll() {

        List<Genre> genres = genreRepo.findAll();

        return genres;
    }

    public Optional<Genre> getById(Integer id) {

        Optional<Genre> genre = genreRepo.findById(id);

        return genre;

    }

    public Genre storeOrUpdate(Genre genre) {

        return genreRepo.save(genre);

    }

    public void deleteById(Integer id) {

        Genre genreToDelete = genreRepo.findById(id).get();

        for (Game game : genreToDelete.getGames()) {

            game.getGenres().remove(genreToDelete);

        }

        genreRepo.delete(genreToDelete);

    }

}
