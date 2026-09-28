package org.lessons.java.spring_progetto_finale.repositories;

import java.util.List;

import org.lessons.java.spring_progetto_finale.model.Console;
import org.lessons.java.spring_progetto_finale.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GamesRepo extends JpaRepository<Game, Integer> {

    List<Game> findByNameContaining(String name);

    List<Game> findByAvailableConsoles(Console availableConsoles);

}
