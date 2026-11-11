package org.lessons.java.spring_progetto_finale.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) {

        http.authorizeHttpRequests(requests -> requests
                .requestMatchers("/edit/**", "/create")
                .hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/delete/**", "/genres/delete/**", "/consoles/delete/**")
                .hasAuthority("ADMIN")
                .requestMatchers("/genres/create", "/genres/edit/**")
                .hasAuthority("ADMIN")
                .requestMatchers("/consoles/create", "/consoles/edit/**")
                .hasAuthority("ADMIN")
                .requestMatchers("/**")
                .permitAll())
                .formLogin(form -> form.defaultSuccessUrl("/").loginPage("/login"))
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

}
