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
    private String type;

    private String besoin;
    private Number montant;

    public enum Status {
        PENDING, CONFIRMED, REFUSED
    }

    @Enumerated(EnumType.STRING)
    private Status status;
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private User client;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;


}
