package org.example.projet_dev.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Demande {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String Type_project;
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
    private Client client;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
