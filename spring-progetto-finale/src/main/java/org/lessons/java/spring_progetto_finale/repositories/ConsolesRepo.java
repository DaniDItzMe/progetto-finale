package org.lessons.java.spring_progetto_finale.repositories;

import org.lessons.java.spring_progetto_finale.model.Console;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsolesRepo extends JpaRepository<Console, Integer> {

}
