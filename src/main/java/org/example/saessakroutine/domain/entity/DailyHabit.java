package org.example.saessakroutine.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class DailyHabit{
    @Id
    private Long dayId;

    @OneToOne(cascade = CascadeType.PERSIST)
    @MapsId
    private Habit habit;

    private Integer totalRepeat;

    @Builder
    public DailyHabit(Habit habit, int totalRepeat){
        this.habit = habit;
        this.totalRepeat = totalRepeat;
    }


    public void HabitUpdate_day(Integer totalRepeat){
        this.totalRepeat = totalRepeat;
    }
}