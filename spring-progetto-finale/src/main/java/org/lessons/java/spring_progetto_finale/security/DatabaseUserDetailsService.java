package org.lessons.java.spring_progetto_finale.security;

import java.util.Optional;

import org.lessons.java.spring_progetto_finale.model.User;
import org.lessons.java.spring_progetto_finale.repositories.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class DatabaseUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> userAttempt = userRepo.findByUsername(username);

        if (userAttempt.isEmpty()) {
            throw new UsernameNotFoundException("There is no user with this username");
        }

        return new DatabaseUserDetails(userAttempt.get());

    }

}
