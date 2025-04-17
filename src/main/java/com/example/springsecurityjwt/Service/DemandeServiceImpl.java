package com.example.springsecurityjwt.Service;

import com.example.springsecurityjwt.Repository.DemandeRepository;
import com.example.springsecurityjwt.entities.Demande;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DemandeServiceImpl implements DemandeService {

    private final DemandeRepository demandeRepository;

    // Constructor-based Dependency Injection for DemandeRepository
    public DemandeServiceImpl(DemandeRepository demandeRepository) {
        this.demandeRepository = demandeRepository;
    }

    // Corrected method to handle status as enum
    @Override
    public List<Demande> getDemandesByStatus(Demande.Status status) {
        return demandeRepository.findByStatus(status);
    }

    @Override
    public Demande saveDemande(Demande demande) {
        // Set default status to PENDING if not provided
        if (demande.getStatus() == null) {
            demande.setStatus(Demande.Status.PENDING);
        }
        return demandeRepository.save(demande);
    }

    @Override
    public Optional<Demande> getDemandeById(Long id) {
        return demandeRepository.findById(id);
    }

    @Override
    public List<Demande> getDemandesByUserId(Long userId) {
        return demandeRepository.findByUserId(userId);
    }

    @Override
    public List<Demande> getDemandesByUserIdAndStatus(Long userId, Demande.Status status) {
        return demandeRepository.findByUserIdAndStatus(userId, status);
    }

    @Override
    public Demande updateDemande(Demande demande) {
        // Additional business logic can be added here
        return demandeRepository.save(demande);
    }

    @Override
    public void deleteDemande(Long id) {
        demandeRepository.deleteById(id);
    }
    @Override
    public List<Demande> getAllDemandes() {
        return demandeRepository.findAll();
    }
}
