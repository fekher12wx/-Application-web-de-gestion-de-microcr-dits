package com.example.springsecurityjwt.Service;

import com.example.springsecurityjwt.entities.Demande;
import java.util.List;
import java.util.Optional;

public interface DemandeService {

    Demande saveDemande(Demande demande);


    Optional<Demande> getDemandeById(Long id);

    List<Demande> getDemandesByUserId(Long userId);

    List<Demande> getDemandesByUserIdAndStatus(Long userId, Demande.Status status);

    Demande updateDemande(Demande demande);

    void deleteDemande(Long id);

    // Update to accept Demande.Status enum type
    List<Demande> getDemandesByStatus(Demande.Status status);  // <-- Change here

    List<Demande> getAllDemandes();

}
