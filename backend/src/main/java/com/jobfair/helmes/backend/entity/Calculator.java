package com.jobfair.helmes.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "calculation_sessions")
@Getter
@NoArgsConstructor
public class Calculator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    @Setter(AccessLevel.NONE) // ID is immutable once set
    private Long id;

    @Column(name = "session_id", nullable = false, unique = true, length = 36)
    @Setter(AccessLevel.NONE)
    private String sessionId;

    @Column(name = "gross_salary")
    @Setter
    private Double grossSalary;

    @Column(name = "birth_date", nullable = false)
    @Setter
    private LocalDate birthDate;

    @Column(name = "created_at", nullable = false)
    @Setter(AccessLevel.NONE)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @Setter(AccessLevel.NONE)
    private LocalDateTime updatedAt;

    @Column(name = "expires_at", nullable = false)
    @Setter(AccessLevel.NONE)
    private LocalDateTime expiresAt; // TTL

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void touch() {
        this.updatedAt = LocalDateTime.now();
    }

    public void initializeExpiration(long days) {
        this.expiresAt = LocalDateTime.now().plusDays(days);
    }

    public void refreshExpiration(long days) {
        this.expiresAt = LocalDateTime.now().plusDays(days);
    }

    public static Calculator create(String sessionId, Double grossSalary, LocalDate birthDate) {
        Calculator c = new Calculator();
        c.sessionId = sessionId;
        c.grossSalary = grossSalary;
        c.birthDate = birthDate;
        return c;
    }

    public void updateData(Double grossSalary, LocalDate birthDate) {
        this.grossSalary = grossSalary;
        this.birthDate = birthDate;
    }
}
