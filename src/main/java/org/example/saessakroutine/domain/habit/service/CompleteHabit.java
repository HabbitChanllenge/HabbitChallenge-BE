package org.example.saessakroutine.domain.habit.service;

import lombok.RequiredArgsConstructor;
import org.example.saessakroutine.domain.habit.persistence.dto.request.HabitCompleteRequest;
import org.example.saessakroutine.domain.habit.persistence.dto.status.exceptions.BadRequestException;
import org.example.saessakroutine.domain.habit.persistence.dto.status.exceptions.NotThingException;
import org.example.saessakroutine.domain.habit.service.count.WeekStreak;
import org.example.saessakroutine.domain.entity.DailyHabit;
import org.example.saessakroutine.domain.entity.Habit;
import org.example.saessakroutine.domain.repository.DayRepository;
import org.example.saessakroutine.domain.repository.HabitRepository;
import org.example.saessakroutine.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompleteHabit {
    private final HabitRepository habitRepository;
    private final DayRepository dayRepository;
    private final UserRepository userRepository;
    private final WeekStreak weekStreak;

    public String Complete(Long id, HabitCompleteRequest request){
        Habit habit = habitRepository.findById(id).orElseThrow(() -> new NotThingException("인증할 습관이 없습니다."));
        if(habit.getPeriodType().equals("WEEKLY")){
            if(request.getCompletedCount() == 1){
                weekStreak.CountWeek(id, habit.isCompleted(), true); //일주일 습관 인증 스트릭 계산을 위해서
                habit.CompleteUpdateWeek(request.getCompletedCount(), true);
                habit.getUser().updateCompletedHabits(true); //인증한 습관 개수 갱신

            } else if(request.getCompletedCount() < 1){
                weekStreak.CountWeek(id, habit.isCompleted(), false); //일주일 습관 인증 스트릭 계산을 위해서
                habit.CompleteUpdateWeek(request.getCompletedCount(), false);
            } else {
                throw new BadRequestException("인증요청이 올바르지 않습니다.");
            }
        } else {
            DailyHabit dailyHabit = dayRepository.findById(id).orElseThrow(()->new IllegalArgumentException("하루기준 습관이 생성되어있지 않습니다."));
            if(request.getCompletedCount() == dailyHabit.getTotalRepeat()){
                habit.CompleteUpdateDay(request.getCompletedCount(), true); //일주일기준 습관과 스트릭 계산이 달라 분리

                habit.getUser().updateCompletedHabits(true);//인증한 습관개수 갱신

            } else if(request.getCompletedCount() > dailyHabit.getTotalRepeat()){
                throw new BadRequestException("인증요청이 올바르지 않습니다.");
            } else {
                habit.CompleteUpdateDay(request.getCompletedCount(), false);
            }
        }
        habitRepository.save(habit); //이걸 안하면 DB에 반영이 안됨
        userRepository.save(habit.getUser());
        if (habit.getUser().getCompletedHabits() == habit.getUser().getAllHabits()){
            habit.getUser().updateAllStreak(true);
            userRepository.save(habit.getUser());
        }
        return "습관 인증 정보가 수정되었습니다.";
    }
}