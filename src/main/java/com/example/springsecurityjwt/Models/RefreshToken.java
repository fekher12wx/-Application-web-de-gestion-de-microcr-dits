package com.example.springsecurityjwt.Models;

import com.example.springsecurityjwt.entities.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
@Data

@Entity(name="refreshtoken")

public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;
    @Column(nullable = false)
    private String token;
    @Column(nullable = false)
    private Instant expiryDate;

}
