package org.example.projet_dev.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Employee extends User {

    private String Matricule;
    private String telephone;

    @OneToMany(mappedBy = "employee")
    private List<Demande> demandes;


}
