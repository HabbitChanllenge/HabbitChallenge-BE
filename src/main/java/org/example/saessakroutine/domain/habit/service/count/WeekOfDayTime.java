package org.example.saessakroutine.domain.habit.service.count;

import lombok.RequiredArgsConstructor;
import org.example.saessakroutine.domain.entity.WeeklyHabit;
import org.example.saessakroutine.domain.repository.WeekRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
public class WeekOfDayTime {
    private final WeekRepository weekRepository;


    @Transactional
    public void DayOfWeeks(int dayOfWeek){
        List<WeeklyHabit> weeklyHabits = weekRepository.findAll().stream().filter(weeklyHabit1 -> weeklyHabit1.getWeekOfDay().contains(dayOfWeek)).toList();
        List<WeeklyHabit> otherWeeklyHabit = weekRepository.findAll().stream().filter(weeklyHabit1 -> !weeklyHabit1.getWeekOfDay().contains(dayOfWeek)).toList();

        for(WeeklyHabit weeklyHabit : weeklyHabits){
            weeklyHabit.getHabit().CompletedUpdate(false); //인증해야 하는 요일이 화요일일때 버튼 활성화
        }
        for(WeeklyHabit weeklyHabit : otherWeeklyHabit){
            weeklyHabit.getHabit().CompletedUpdate(true);
        }

    }
}
