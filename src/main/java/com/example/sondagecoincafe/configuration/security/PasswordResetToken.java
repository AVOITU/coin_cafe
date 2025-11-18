package com.example.sondagecoincafe.configuration.security;

import com.example.sondagecoincafe.bo.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Setter @Getter
public class PasswordResetToken {
    @Id
    @GeneratedValue
    private Long id;
    private String token;
    @OneToOne
    private User user;
    private LocalDateTime expiresAt;
}
