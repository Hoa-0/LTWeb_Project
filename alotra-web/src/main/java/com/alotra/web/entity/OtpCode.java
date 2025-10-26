package com.alotra.web.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "OtpCodes") // Updated table name to match database
public class OtpCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id; // Changed to Long to match database

    @Column(name = "Email", nullable = false, length = 100)
    private String email;

    @Column(name = "Code", nullable = false, length = 10)
    private String code;

    @Column(name = "Type", length = 20)
    private String type; // register, reset_password, etc.

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt;

    @Column(name = "ExpiresAt")
    private LocalDateTime expiresAt;

    @Column(name = "Used")
    private Boolean used = false;

    @Column(name = "UsedAt")
    private LocalDateTime usedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (expiresAt == null) {
            expiresAt = createdAt.plusMinutes(10); // Default 10 minutes expiry
        }
    }

    // Additional getters for compatibility
    public LocalDateTime getUsedAt() {
        return usedAt;
    }

    public void setUsedAt(LocalDateTime usedAt) {
        this.usedAt = usedAt;
    }
}