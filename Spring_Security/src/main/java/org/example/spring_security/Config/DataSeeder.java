package org.example.spring_security.Config;

import org.example.spring_security.Entity.Author;
import org.example.spring_security.Entity.BlogPost;
import org.example.spring_security.Repository.AuthorRepository;
import org.example.spring_security.Repository.BlogPostRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedData(AuthorRepository authorRepository, BlogPostRepository blogPostRepository) {
        return args -> {
            Author a1 = authorRepository.save(new Author("Aiko Tanaka"));
            Author a2 = authorRepository.save(new Author("Jonas Schmidt"));
            authorRepository.save(new Author("Cas Van Dijk"));

            blogPostRepository.save(new BlogPost(
                    "Boost Your Productivity with 10 Easy Tips",
                    "Productivity - we all want it but it seems ...",
                    a1
            ));

            blogPostRepository.save(new BlogPost(
                    "How to Focus",
                    "Do you ever sit down to work and find yourself ...",
                    a1
            ));

            blogPostRepository.save(new BlogPost(
                    "Learn to Speed Read in 30 Days",
                    "Knowledge, not ability, is the great determiner of ...",
                    a2
            ));
        };
    }
}
