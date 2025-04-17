package com.example.springsecurityjwt.Controllers;

import com.example.springsecurityjwt.Service.DemandeService;
import com.example.springsecurityjwt.Service.EmployeService;
import com.example.springsecurityjwt.entities.Demande;
import com.example.springsecurityjwt.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeService employeeService;
    private final DemandeService demandeService;

    public EmployeeController(EmployeService employeeService, DemandeService demandeService) {
        this.employeeService = employeeService;
        this.demandeService = demandeService;
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployes();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-matricule/{matricule}")
    public ResponseEntity<Employee> getEmployeeByMatricule(@PathVariable String matricule) {
        return employeeService.getEmployeeByMatricule(matricule)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee savedEmployee = employeeService.saveEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable Long id,
            @RequestBody Employee employeeDetails) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, employeeDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    // Get all demandes with a specific status (e.g., PENDING, CONFIRMED, REFUSED)
    @GetMapping("/{employeeId}/demandes/status")
    public ResponseEntity<List<Demande>> getDemandesByStatus(
            @PathVariable Long employeeId,
            @RequestParam("status") Demande.Status status) {

        List<Demande> demandes = demandeService.getDemandesByStatus(status);
        return ResponseEntity.ok(demandes);
    }

    // Approve a demande
    @PutMapping("/{employeeId}/demandes/{demandeId}/approve")
    public ResponseEntity<?> approveDemande(
            @PathVariable Long employeeId,
            @PathVariable Long demandeId) {

        return demandeService.getDemandeById(demandeId)
                .map(demande -> {
                    if (!"PENDING".equals(demande.getStatus().toString())) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body("Only pending demandes can be approved");
                    }

                    // Get the employee
                    return employeeService.getEmployeeById(employeeId)
                            .map(employee -> {
                                // Update the demande
                                demande.setStatus(Demande.Status.CONFIRMED);
                                demande.setEmployee(employee);
                                Demande updatedDemande = demandeService.updateDemande(demande);
                                return ResponseEntity.ok(updatedDemande);
                            })
                            .orElse(ResponseEntity.notFound().build());
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Refuse a demande
    @PutMapping("/{employeeId}/demandes/{demandeId}/refuse")
    public ResponseEntity<?> refuseDemande(
            @PathVariable Long employeeId,
            @PathVariable Long demandeId) {

        return demandeService.getDemandeById(demandeId)
                .map(demande -> {
                    if (!"PENDING".equals(demande.getStatus().toString())) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body("Only pending demandes can be refused");
                    }

                    // Get the employee
                    return employeeService.getEmployeeById(employeeId)
                            .map(employee -> {
                                // Update the demande
                                demande.setStatus(Demande.Status.REFUSED);
                                demande.setEmployee(employee);
                                Demande updatedDemande = demandeService.updateDemande(demande);
                                return ResponseEntity.ok(updatedDemande);
                            })
                            .orElse(ResponseEntity.notFound().build());
                })
                .orElse(ResponseEntity.notFound().build());
    }
}