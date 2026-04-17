package com.ironhack.lab4_1_spring_security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // POST, PUT, DELETE üçün vacibdir
                .authorizeHttpRequests(auth -> auth
                        // GET sorğuları hamıya açıqdır
                        .requestMatchers(HttpMethod.GET, "/api/**").permitAll()
                        // Digər bütün işlər (Add, Update, Delete) üçün ADMIN lazımdır
                        .anyRequest().hasRole("ADMIN")
                )
                .httpBasic(Customizer.withDefaults()); // Postman-da yoxlamaq üçün

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // Şifrənin qarşısına {noop} artırmaqla Spring-ə deyirik ki,
        // bu şifrəni şifrələmə (No Operation)
        UserDetails admin = User.builder()
                .username("admin")
                .password("{noop}admin123")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin);
    }
}