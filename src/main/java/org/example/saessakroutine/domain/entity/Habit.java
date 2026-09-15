package org.example.saessakroutine.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.saessakroutine.user.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor // 파라미터가 없는 생성자를 자동으로 생성해준다.
//JPA가 DB의 정보를 객체로 변환하게 하기 위해 필요함.
public class Habit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long habit_id;
    //유저 기본키는 나중에 받아오기
    private String name;
    private boolean completed;
    private int streak;
    private int completedCount; //여러번 인증해야하는 경우
    private String periodType;
    //마지막 습관 인증날짜 저장하는 필드 삭제.
    //전체 스트릭을 판단하기 위한 필드였는데 0시가 되었을때 모든 습관이 인증 되었는지만 확인하면 되므로.
    //=====================================================================
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    //===================================================================== 카테고리
    @ElementCollection //클래스는 아니나, 클래스 처럼 사용할 수 있도록 설명해주는? 어노테이션. 1:N관계에서 많이 사용함 (정확한 내요은 공부하기)
    @CollectionTable(name = "category", joinColumns = @JoinColumn(name = "habitId"))
    //위의 어노테이션으로 설정한 테이블을 실제로 구현하기 위한 설명. 테이블의 이름, 상속(?)하는 테이블의 아이디를 알려준다.
    @Column(name = "categories")
    //데이터베이스의 categories에 매핑한다는 뜻으로, 아래 코드에서 바로 데이터베이스의 categories칼럼에 접근하기 위해 사용함.
    private List<String> category = new ArrayList<>(); //동적 할당을 해주는 ArrayList를 사용하여 많은 수를 저장할 수 있는 카테고리를 저장 할 수 있도록 한다.

    //===================================================================== 생성자
    @Builder
    public Habit(String name, String periodType, List<String> category, User user){
        this.name = name;
        this.periodType = periodType;
        this.category = category;
        this.user = user;
    }

    //====================================================================== Daily와 Weekly엔티티와의 관계를 이어주기 위한 코드
    @OneToOne(mappedBy = "habit", cascade = CascadeType.REMOVE)
    private DailyHabit dailyHabit;

    public void Daily (DailyHabit dailyHabit){
        this.dailyHabit = dailyHabit;
    }

    @OneToOne(mappedBy = "habit", cascade = CascadeType.REMOVE)
    private WeeklyHabit weeklyHabit;

    public void Weekly (WeeklyHabit weeklyHabit){
        this.weeklyHabit = weeklyHabit;
    }




    //======================================================================= 습관 인증후 인증 기록 저장을 위한 코드들
    public void CompleteUpdateDay(int completedCount, boolean completed){
        this.completedCount = completedCount;
        if(this.completed){
            if(!completed){
                streak--;
            }
        }
        if(completed){
            streak++;
        }
        this.completed = completed;
    }
    public void CompleteUpdateWeek(int completedCount, boolean completed){
        this.completedCount = completedCount;
        this.completed = completed;
    }

    //======================================================================= 습관 업데이트를 위한 코드
    public void UpdateHabits_name(String name){
        this.name = name;
    }
    public void UpdateHabits_category(List<String> category){
        this.category = category;
    }
}