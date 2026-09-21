package org.example.saessakroutine.domain.habit.service.count;

import lombok.RequiredArgsConstructor;
import org.example.saessakroutine.domain.entity.DailyHabit;
import org.example.saessakroutine.domain.repository.DayRepository;
import org.example.saessakroutine.user.entity.User;
import org.example.saessakroutine.user.repository.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DayTime {
    private final DayRepository dayRepository;
    private final UserRepository userRepository;

    private final WhatWeekOfDay weekOfDay;

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void NewDay(){
        System.out.println("오류 없음!!");
        List<DailyHabit> dailyHabits = dayRepository.findAll().stream().toList();
        for(DailyHabit dailyHabit : dailyHabits){
            if(!dailyHabit.getHabit().isCompleted()){
                if ((dailyHabit.getHabit().getUser().isAllStreakCount())){
                    dailyHabit.getHabit().getUser().updateAllStreakCount(false); //하나라도 인증을 안하면 전체 스트릭 초기화를 하기 위해 표시
                }
                if(dailyHabit.getHabit().getUser().getAllStreak() != 0){
                    dailyHabit.getHabit().getUser().updateAllStreak(0);
                }
            }
            dailyHabit.getHabit().CompletedUpdate(false); //전부 false로 바꿔서 인증버튼 활성화
        }
//        for(int i = 0; i < dailyHabits.size(); i++){
//            Habit habit = dailyHabits.get(i).getHabit();
//            if(!habit.isCompleted()){
//                habit.HabitStreak(false);
//                if(habit.getUser().isAllStreakCount()){
//                    habit.getUser().updateAllStreakCount(false); //하나라도 인증을 안하면 전체 스트릭 초기화를 하기 위해 표시
//                }
//                if(habit.getUser().getAllStreak() != 0){
//                    habit.getUser().updateAllStreak(0);
//                }
//            } else {
//                habit.HabitStreak(true);
//            }
//            habit.CompletedUpdate(false); //전부 false로 바꿔서 인증버튼 활성화
//            habitRepository.save(habit);
//        }
        //======================================================================일주일 요일판단
        weekOfDay.WhatDay();
        //=======================================================================전체 스트릭 판단

        List<User> users = userRepository.findAll().stream().toList();
        for(User user : users){
            user.updateAllStreakCount(user.isAllStreakCount());
        }
    }
}
