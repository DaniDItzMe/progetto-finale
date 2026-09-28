package org.lessons.java.spring_progetto_finale.rest_controllers;

import java.util.Map;

import org.lessons.java.spring_progetto_finale.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    @Autowired
    private UsersService userService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> index(Authentication authentication) {

        System.out.println("AUTHENTICATING");

        return userService.auth(authentication);

    }

}
