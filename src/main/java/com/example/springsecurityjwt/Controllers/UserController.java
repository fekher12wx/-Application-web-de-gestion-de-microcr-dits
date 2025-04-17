package com.example.springsecurityjwt.Controllers;

import com.example.springsecurityjwt.Service.DemandeService;
import com.example.springsecurityjwt.Service.UserDetailsImpl;
import com.example.springsecurityjwt.Service.UserMessageService;
import com.example.springsecurityjwt.Service.UserService;
import com.example.springsecurityjwt.entities.Demande;
import com.example.springsecurityjwt.entities.User;
import com.example.springsecurityjwt.entities.UserMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final DemandeService demandeService;
    private final UserMessageService userMessageService;

    public UserController(DemandeService demandeService, UserService userService , UserMessageService userMessageService) {
        this.demandeService = demandeService;
        this.userService = userService;
        this.userMessageService = userMessageService;
    }

    // Send a message (user to employee)
    @PostMapping("/{senderId}/message/{receiverId}")
    public ResponseEntity<UserMessage> sendMessage(
            @PathVariable Long senderId,
            @PathVariable Long receiverId,
            @RequestBody UserMessage message
    ) {
        // Set sender and receiver IDs
        message.setSenderId(senderId);
        message.setReceiverId(receiverId);


        message.setSentAt(LocalDateTime.now());

        // Save the message and return it
        UserMessage sentMessage = userMessageService.sendMessage(message);
        return ResponseEntity.status(HttpStatus.CREATED).body(sentMessage);
    }
    @GetMapping("/{userId}/messages")
    public ResponseEntity<List<UserMessage>> getAllMessages(@PathVariable Long userId) {
        // Get messages for the user (both sent and received)
        List<UserMessage> messages = new ArrayList<>();
        messages.addAll(userMessageService.getMessagesForReceiver(userId)); // Inbox
        messages.addAll(userMessageService.getMessagesFromSender(userId));   // Sent Messages

        return ResponseEntity.ok(messages);
    }
    @GetMapping("/profile")
    public ResponseEntity<User> getCurrentUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        User user = userService.getUserById(userDetails.getId());
        return ResponseEntity.ok(user);
    }
    @PutMapping("/profile")
    public ResponseEntity<User> updateCurrentUserProfile(@RequestBody User.UserUpdateRequest updateRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        User updatedUser = userService.updateUser(userDetails.getId(), updateRequest);
        return ResponseEntity.ok(updatedUser);
    }


    // Create a new demande
    @PostMapping("/{userId}/demandes")
    public ResponseEntity<Demande> createDemande(
            @PathVariable Long userId,
            @RequestBody Demande demande) {
        demande.setUserId(userId);
        Demande savedDemande = demandeService.saveDemande(demande);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDemande);
    }

    // Get all demandes for a user
    @GetMapping("/{userId}/demandes")
    public ResponseEntity<List<Demande>> getUserDemandes(@PathVariable Long userId) {
        List<Demande> demandes = demandeService.getDemandesByUserId(userId);
        return ResponseEntity.ok(demandes);
    }

    // Get all confirmed demandes for a user
    @GetMapping("/{userId}/demandes/confirmed")
    public ResponseEntity<List<Demande>> getConfirmedDemandes(@PathVariable Long userId) {
        List<Demande> confirmedDemandes = demandeService.getDemandesByUserIdAndStatus(userId, Demande.Status.CONFIRMED);
        return ResponseEntity.ok(confirmedDemandes);
    }

    // Get all refused demandes for a user
    @GetMapping("/{userId}/demandes/refused")
    public ResponseEntity<List<Demande>> getRefusedDemandes(@PathVariable Long userId) {
        List<Demande> refusedDemandes = demandeService.getDemandesByUserIdAndStatus(userId, Demande.Status.REFUSED);
        return ResponseEntity.ok(refusedDemandes);
    }

    // Get all pending demandes for a user
    @GetMapping("/{userId}/demandes/pending")
    public ResponseEntity<List<Demande>> getPendingDemandes(@PathVariable Long userId) {
        List<Demande> pendingDemandes = demandeService.getDemandesByUserIdAndStatus(userId, Demande.Status.PENDING);
        return ResponseEntity.ok(pendingDemandes);
    }

    // Update a demande (user can only update pending demandes)
    @PutMapping("/{userId}/demandes/{demandeId}")
    public ResponseEntity<?> updateDemande(
            @PathVariable Long userId,
            @PathVariable Long demandeId,
            @RequestBody Demande demandeDetails) {
        return demandeService.getDemandeById(demandeId)
                .map(demande -> {
                    if (!demande.getUserId().equals(userId)) {
                        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                    }
                    if (demande.getStatus() != Demande.Status.PENDING) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                    }
                    demandeDetails.setId(demandeId);
                    demandeDetails.setUserId(userId);
                    return ResponseEntity.ok(demandeService.updateDemande(demandeDetails));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete a demande (user can only delete pending demandes)
    @DeleteMapping("/{userId}/demandes/{demandeId}")
    public ResponseEntity<Object> deleteDemande(
            @PathVariable Long userId,
            @PathVariable Long demandeId) {
        return demandeService.getDemandeById(demandeId)
                .map(demande -> {
                    if (!demande.getUserId().equals(userId)) {
                        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                    }
                    if (demande.getStatus() != Demande.Status.PENDING) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                    }
                    demandeService.deleteDemande(demandeId);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
