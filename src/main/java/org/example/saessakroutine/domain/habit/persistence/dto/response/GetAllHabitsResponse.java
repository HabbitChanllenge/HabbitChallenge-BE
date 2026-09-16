package org.example.saessakroutine.domain.habit.persistence.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.saessakroutine.domain.entity.Habit;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Transactional
public class GetAllHabitsResponse {
    private Long habit_id;
    private String name;
    private boolean completed;
    private int streak;
    private int completedCount;
    private String periodType;
    private List<String> category = new ArrayList<>();
    private List<Integer> weekOfDay = new ArrayList<>();
    private int totalRepeat;

    public GetAllHabitsResponse(Habit habit){
        this.habit_id = habit.getHabit_id();
        this.name = habit.getName();
        this.periodType = habit.getPeriodType();
        this.completed = habit.isCompleted();
        this.completedCount = habit.getCompletedCount();
        this.streak = habit.getStreak();
        this.category = habit.getCategory();
        if (habit.getPeriodType().equals("DAILY")){ //DailyHabit에 접근할지, WeeklyHabit에 접근할지 판단하는 부분
            this.totalRepeat = habit.getDailyHabit().getTotalRepeat();
        } else {
            this.weekOfDay = habit.getWeeklyHabit().getWeekOfDay();
        }
    }
}
