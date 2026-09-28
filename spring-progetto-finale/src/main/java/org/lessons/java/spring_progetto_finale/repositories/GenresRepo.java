package org.lessons.java.spring_progetto_finale.repositories;

import org.lessons.java.spring_progetto_finale.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenresRepo extends JpaRepository<Genre, Integer> {

}
