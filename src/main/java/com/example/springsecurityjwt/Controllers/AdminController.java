package com.example.springsecurityjwt.Controllers;

import com.example.springsecurityjwt.Service.DemandeService;
import com.example.springsecurityjwt.Service.UserService;
import com.example.springsecurityjwt.entities.Demande;
import com.example.springsecurityjwt.entities.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final DemandeService demandeService;

    // Constructor injection
    public AdminController(UserService userService,DemandeService demandeService) {
        this.userService = userService;
        this.demandeService=demandeService;
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/demandes")
    public ResponseEntity<List<Demande>> getAllDemandes() {
        List<Demande> demandes = demandeService.getAllDemandes();
        return ResponseEntity.ok(demandes);
    }

    // Get a specific demande by ID (admin only)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/demandes/{id}")
    public ResponseEntity<Demande> getDemandeById(@PathVariable Long id) {
        return demandeService.getDemandeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    // Endpoint to get all users, accessible only by admins
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers(); // Assuming this method exists in your service layer
        return ResponseEntity.ok(users);
    }

    // Endpoint to get a user by their ID
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id); // Adjusting the service method to return a User instead of Optional
        return ResponseEntity.ok(user); // Directly return the user, assuming it's never null
    }

    // Endpoint to create a new user
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userService.createUser(user); // Assuming createUser is implemented in the service layer
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    // Endpoint to update an existing user
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        User updatedUser = userService.updateUser(id, userDetails); // Assuming this is properly handled in the service
        return ResponseEntity.ok(updatedUser);
    }

    // Endpoint to delete a user
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id); // Assuming deleteUser method exists in the service layer
        return ResponseEntity.noContent().build();
    }
}
