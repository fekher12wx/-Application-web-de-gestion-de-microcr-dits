package com.example.springsecurityjwt.Repository;

import com.example.springsecurityjwt.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String Username);
    boolean existsByUsername(String Username);
    boolean existsByEmail(String Email);
    Optional<User> findByEmail(String Email);



}
