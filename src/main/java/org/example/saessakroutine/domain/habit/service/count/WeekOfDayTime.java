package org.example.saessakroutine.domain.habit.service.count;

import lombok.RequiredArgsConstructor;
import org.example.saessakroutine.domain.entity.Habit;
import org.example.saessakroutine.domain.entity.WeeklyHabit;
import org.example.saessakroutine.domain.repository.HabitRepository;
import org.example.saessakroutine.domain.repository.WeekRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
public class WeekOfDayTime {
    private final WeekRepository weekRepository;
    private final HabitRepository habitRepository;


    @Transactional
    public void DayOfWeeks(int dayOfWeek){
        List<WeeklyHabit> weeklyHabit = weekRepository.findAll().stream().filter(weeklyHabit1 -> weeklyHabit1.getWeekOfDay().contains(dayOfWeek)).toList();
        List<WeeklyHabit> otherWeeklyHabit = weekRepository.findAll().stream().filter(weeklyHabit1 -> !weeklyHabit1.getWeekOfDay().contains(dayOfWeek)).toList();

        for(int i=0; i < weeklyHabit.size(); i++){
            Habit habit = weeklyHabit.get(i).getHabit();
            habit.CompletedUpdate(false); //인증해야 하는 요일이 화요일일때 버튼 활성화
            habitRepository.save(habit);
        }
        for(int i=0; i < otherWeeklyHabit.size(); i++){
            Habit otherHabit = otherWeeklyHabit.get(i).getHabit();
            otherHabit.CompletedUpdate(true);
            habitRepository.save(otherHabit);
        }

    }
}
