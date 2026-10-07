package org.lessons.java.spring_progetto_finale.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    public ResponseEntity<Map<String, Object>> auth(Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {

            return ResponseEntity
                    .ok(Map.of("authenticated", false, "username", "", "role", ""));

        }

        String role = authentication.getAuthorities().iterator().next().getAuthority();

        return ResponseEntity.ok(Map.of("authenticated", authentication.isAuthenticated(), "username",
                authentication.getName(), "role", role));

    }

}
