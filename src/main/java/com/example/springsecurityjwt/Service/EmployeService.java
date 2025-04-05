package com.example.springsecurityjwt.Service;

import com.example.springsecurityjwt.Repository.EmployeRepository;
import com.example.springsecurityjwt.entities.Employee;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
@Transactional
public class EmployeService {
private final EmployeRepository EmployeRepository;
@Autowired
public EmployeService(EmployeRepository employeRepository) {
        this.EmployeRepository = employeRepository;
    }
    public List<Employee> getAllEmployes() {
        return EmployeRepository.findAll();
    }
    public Optional<Employee> getEmployeeById(Long id) {
        return EmployeRepository.findById(id);
    }
    public Optional<Employee> getEmployeeByMatricule(String matricule) {
        return EmployeRepository.findByMatricule(matricule);
    }
    public Employee saveEmployee(Employee employee) {
        return EmployeRepository.save(employee);
    }
    public Employee updateEmployee(Long id, Employee employeeDetails) {
        return EmployeRepository.findById(id)
                .map(employee -> {
                    employee.setEmail(employeeDetails.getEmail());
                    employee.setMatricule(employeeDetails.getMatricule());
                    employee.setPassword(employeeDetails.getPassword());
                    return EmployeRepository.save(employee);
                })
                .orElseThrow(() -> new RuntimeException("Employee not found with id " + id));
    }
    public void deleteEmployee(Long id) {
        EmployeRepository.deleteById(id);
    }
}
