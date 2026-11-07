package com.magedalaa.serviceshub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * An account on the platform. subscribed is the single switch that decides
 * whether a user can use the gated services - everything else (signup,
 * login) exists to get a user to the point of flipping that switch.
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Email
    @Column(unique = true)
    private String email;

    @NotBlank
    private String passwordHash;

    private boolean subscribed;

    private LocalDateTime createdAt;

    protected User() {
        // required by JPA
    }

    public User(String email, String passwordHash) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.createdAt = LocalDateTime.now();
        this.subscribed = false;
    }

    public void subscribe() {
        this.subscribed = true;
    }

    public void unsubscribe() {
        this.subscribed = false;
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public boolean isSubscribed() { return subscribed; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
