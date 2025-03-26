package com.example.springsecurityjwt.Repository;

import com.example.springsecurityjwt.entities.Demande;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DemandeRepository extends JpaRepository<Demande, Long> {
    Optional<Demande> findByName(String name);
}
