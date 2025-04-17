package com.example.springsecurityjwt.Repository;

import com.example.springsecurityjwt.entities.Demande;
import com.example.springsecurityjwt.entities.Employee;
import com.example.springsecurityjwt.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DemandeRepository extends JpaRepository<Demande, Long> {
    // Find by user ID
    List<Demande> findByUserId(Long userId);

    // Find by user ID and status
    List<Demande> findByUserIdAndStatus(Long userId, Demande.Status status);

    // Find unassigned pending demandes (JPQL query)
    @Query("SELECT d FROM Demande d WHERE d.employee IS NULL AND d.status = com.example.springsecurityjwt.entities.Demande.Status.PENDING")
    List<Demande> findUnassignedPendingDemandes();

    // Alternative method to find unassigned pending demandes (without JPQL)
    // Comment this out if you decide to use only the JPQL version.
    // List<Demande> findByEmployeeIsNullAndStatus(Demande.Status status);

    // Find by client (User object)
    List<Demande> findByClient(User client);

    // Find by status
    List<Demande> findByStatus(Demande.Status status);

    // Find by employee (Employee object)
    List<Demande> findByEmployee(Employee employee);
}
