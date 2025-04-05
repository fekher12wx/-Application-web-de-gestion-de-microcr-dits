package com.example.springsecurityjwt.Repository;

import com.example.springsecurityjwt.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface EmployeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByMatricule(String matricule);
    boolean existsByMatricule(String matricule);
    boolean existsByEmail(String email);
}
