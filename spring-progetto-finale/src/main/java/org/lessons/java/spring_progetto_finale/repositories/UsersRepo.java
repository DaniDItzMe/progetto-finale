package org.lessons.java.spring_progetto_finale.repositories;

import java.util.Optional;

import org.lessons.java.spring_progetto_finale.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepo extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

}
