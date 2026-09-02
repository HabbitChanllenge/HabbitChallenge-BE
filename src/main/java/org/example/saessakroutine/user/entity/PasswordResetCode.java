package org.example.saessakroutine.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "password_reset_codes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PasswordResetCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 60)
    private String email;

    @Column(nullable = false, length = 6)
    private String code;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @Column(nullable = false)
    private boolean verified;

    @Builder
    private PasswordResetCode(String email, String code, LocalDateTime expiresAt) {
        this.email = email;
        this.code = code;
        this.expiresAt = expiresAt;
        this.verified = false;
    }

    public void updateCode(String code, LocalDateTime expiresAt) {
        this.code = code;
        this.expiresAt = expiresAt;
        this.verified = false;
    }

    public void markVerified() {
        this.verified = true;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }

}
