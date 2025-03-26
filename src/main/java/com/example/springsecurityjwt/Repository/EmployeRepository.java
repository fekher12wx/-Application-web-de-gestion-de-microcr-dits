package com.example.springsecurityjwt.Repository;

import com.example.springsecurityjwt.entities.Employee;

import java.util.Optional;

public interface EmployeRepository {
    Optional<Employee> findByMatricule(String matricule);
}
