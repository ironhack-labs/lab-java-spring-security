package com.example.springsecurity.demo.repositories;

import com.example.springsecurity.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    User findByName(String name);
}
