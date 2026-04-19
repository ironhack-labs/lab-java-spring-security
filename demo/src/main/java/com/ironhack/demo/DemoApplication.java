package com.ironhack.demo;

import com.ironhack.demo.entity.Author;
import com.ironhack.demo.entity.AuthorRole;
import com.ironhack.demo.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		System.out.println("Application runs successfully!");
	}

}
