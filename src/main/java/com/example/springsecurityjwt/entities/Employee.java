package com.example.springsecurityjwt.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "employees")
public class Employee extends User {

    @Column(unique = true)
    private String matricule;

    private String telephone;

    @OneToMany(mappedBy = "employee")
    private List<Demande> demandes;

    // Constructors
    public Employee() {
        super();
    }

    public Employee(String username, String email, String password, String matricule, String telephone) {
        super(username, email, password);
        this.matricule = matricule;
        this.telephone = telephone;
    }
}