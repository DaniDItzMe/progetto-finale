package org.lessons.java.spring_progetto_finale.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) {

        http.authorizeHttpRequests(requests -> requests
                .requestMatchers("/api/auth").permitAll()
                .requestMatchers("/edit/**", "/create").hasAuthority("ADMIN")
                .requestMatchers("/api/games/edit/**", "/api/games/create").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/delete/**", "/genres/delete/**", "/consoles/delete/**")
                .hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/games/delete/**", "/api/genres/delete/**",
                        "/api/consoles/delete/**")
                .hasAuthority("ADMIN")
                .requestMatchers("/genres", "/genres/**").hasAuthority("ADMIN")
                .requestMatchers("/api/genres", "/api/genres/**").hasAuthority("ADMIN")
                .requestMatchers("/consoles/create", "/consoles/edit/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/consoles/create", "/api/consoles/edit/**")
                .hasAuthority("ADMIN")
                .requestMatchers("/**").permitAll())
                .logout(logout -> logout.logoutSuccessHandler((request, response, authentication) -> {

                    System.out.println("LOGOUT SUCCESSFULL");
                    response.setStatus(HttpServletResponse.SC_OK);

                }))
                .formLogin(form -> form.successHandler((request, response, authentication) -> {
                    response.setStatus(HttpServletResponse.SC_OK);
                    System.out.println("LOGIN SUCCESS");
                }).failureHandler((request, response, exception) -> {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

                    String error;

                    if (exception instanceof BadCredentialsException) {
                        error = "INVALID CREDENTIALS";
                    } else {
                        error = "AUTHENTICATION FAILED";
                    }

                    System.out.println("LOGIN FALLITO");
                    System.out.println("Username: " + request.getParameter("username"));
                    System.out.println("Exception: " + exception.getClass().getName());
                    System.out.println("Message: " + exception.getMessage());

                    response.setContentType("application/json");
                    response.getWriter().write("""
                                {

                                    "error": "%s"

                                }
                            """.formatted(error));

                }))
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(crsf -> crsf.disable());

        return http.build();
    }

    @Bean
    DaoAuthenticationProvider authProvider() {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService());

        provider.setPasswordEncoder(encoder());

        return provider;

    }

    @Bean
    DatabaseUserDetailsService userDetailsService() {
        return new DatabaseUserDetailsService();
    }

    @Bean
    PasswordEncoder encoder() {

        return PasswordEncoderFactories.createDelegatingPasswordEncoder();

    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(
                List.of("http://localhost:5173"));

        configuration.setAllowedMethods(
                List.of(
                        "GET",
                        "POST",
                        "PUT",
                        "DELETE",
                        "OPTIONS"));

        configuration.setAllowedHeaders(
                List.of("*"));

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

}
