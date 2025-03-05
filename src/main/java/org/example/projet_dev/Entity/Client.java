package org.example.projet_dev.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
public class Client extends User {

    private String Adresse;
    private Date date_naissance;
    private String telephone;

    @OneToMany(mappedBy = "client")
    private List<Demande> demandes;
    @OneToMany(mappedBy = "client")
    private List<Notification> notifications;
}
