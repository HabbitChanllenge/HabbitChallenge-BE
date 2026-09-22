package org.example.saessakroutine.user.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.example.saessakroutine.domain.entity.Habit;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
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

    private boolean allStreakCount; //전체 스트릭 계산을 위해서

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

    public void updateAllStreak(boolean allStreakCount){
        if(allStreakCount){
            this.allStreak++;
        } else {
            this.allStreak = 0;
        }
    }

    public void updateAllStreakCount(boolean allStreakCount){
        this.allStreakCount = allStreakCount;
    }


    @OneToMany(mappedBy = "user")
    private List<Habit> habit = new ArrayList<>();
}
