package org.example.saessakroutine.user;

import jakarta.persistence.*;
import lombok.Builder;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 60)
    private String userId;

    @Column(nullable = false, length = 60)
    private String password;

    @Column(nullable = false, unique = true, length = 60)
    private String email;

    @Column(nullable = false)
    private int allStreak;

    protected User() {}

    @Builder
    public User(String userId, String password, String email) {
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.allStreak = 0;
    }

    public void updateUserId(String userId) {
        this.userId = userId;
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public Long getId() {return id;}
    public String getUserId() {return userId;}
    public String getPassword() {return password;}
    public String getEmail() {return email;}
    public int getAllStreak() {return allStreak;}
}
