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
    private String type;  // ✅ Correct field name

    private String besoin;  // ✅ Follow Java naming conventions (lowercase)
    private Number montant;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private User client;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    // ✅ Correct Getter & Setter
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
