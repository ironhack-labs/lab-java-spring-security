package com.example.springsecurity.demo.repositories;

import com.example.springsecurity.demo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {

    Role findByName(String name);
}
