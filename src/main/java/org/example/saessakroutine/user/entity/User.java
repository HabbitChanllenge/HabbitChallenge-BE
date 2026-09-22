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

    private int allHabits; //생성된 습관의 수를 저장

    private int completedHabits; //완료된 습관드의 수를 저장

    protected User() {}

    @Builder
    public User(String userId, String password, String email) {
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.allStreak = 0;
        this.allHabits = 0;
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

    public void updateAllStreak(boolean completed){
        if(completed){
            this.allStreak++;
        } else {
            this.allStreak = 0;
        }
    }

    public void updateAllHabits(boolean exist){
        if(exist){
            this.allHabits++; //습관을 생성했을 때
        } else {
            this.allHabits--; //습관을 삭제했을 때
        }
    }

    public void updateCompletedHabits(boolean completed){
        if(completed){
            this.completedHabits++; //습관 인증을 완료했을 때
        } else {
            this.completedHabits = 0; //새로운 날이 되었을 때 초기화
        }
    }

    @OneToMany(mappedBy = "user")
    private List<Habit> habit = new ArrayList<>();
}
