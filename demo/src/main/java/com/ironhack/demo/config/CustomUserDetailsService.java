package com.ironhack.demo.config;

import com.ironhack.demo.entity.Author;
import com.ironhack.demo.repository.AuthorRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final AuthorRepository authorRepository;

    public CustomUserDetailsService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Author author = authorRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Author not found"));

        System.out.println("LOGIN ATTEMPT: " + email);

        return org.springframework.security.core.userdetails.User.builder()
                .username(author.getEmail())
                .password(author.getPassword())
                .roles(author.getRole().name())
                .build();
    }
}
