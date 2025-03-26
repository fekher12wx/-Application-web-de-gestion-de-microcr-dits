package com.example.springsecurityjwt.entities;

import com.example.springsecurityjwt.entities.Employee;
import com.example.springsecurityjwt.entities.User;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Demande {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String name;
    private String Type;
    private String Adresse;
    private String Besoin;
    private Number montant;
    private Number montant_garant;
    private String garant;
    private String civilite;
    private String revenu;
    private String credit;
    private String doc;
    private String etat;
    private String Raison;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private User client;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
    public String getType() {
        return Type;
    }

    public void setType(String type) {
        this.Type = type;
    }
}
