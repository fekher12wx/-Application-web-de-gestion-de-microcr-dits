package com.example.springsecurityjwt.Service;

import com.example.springsecurityjwt.Repository.DemandeRepository;
import com.example.springsecurityjwt.entities.Demande;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DemandeService {
    @Autowired
    private DemandeRepository demandeRepository;
    public Optional<Demande> getType(String type) {
        return getType(type);
    }
}
