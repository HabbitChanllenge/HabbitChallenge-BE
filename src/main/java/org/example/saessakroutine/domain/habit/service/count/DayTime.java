package org.example.saessakroutine.domain.habit.service.count;

import lombok.RequiredArgsConstructor;
import org.example.saessakroutine.domain.entity.DailyHabit;
import org.example.saessakroutine.domain.entity.WeeklyHabit;
import org.example.saessakroutine.domain.repository.DayRepository;
import org.example.saessakroutine.domain.repository.WeekRepository;
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
    private final WeekRepository weekRepository;
    private final WhatDayOfWeek weekOfDay;

    @Scheduled(cron = "0 * * * * *")
    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void NewDay(){
        System.out.println("오류 없음!!");
        //======================================================================
        List<User> users = userRepository.findAll().stream().toList();
        for(User user : users){
            if(user.getAllHabits() != user.getCompletedHabits()){ //사용자가 가지고 있는 전체 습관 개수와 완료한 습관의 개수가 같은지 판단
                user.updateAllStreak(false);
            }
            user.updateCompletedHabits(false); //이 사용자의 전체 스트릭 판단은 끝났으므로 0으로 초기화
        }
        //=======================================================================전체 스트릭 판단
        List<DailyHabit> dailyHabits = dayRepository.findAll().stream().toList(); //하루기준 습관만
        for(DailyHabit dailyHabit : dailyHabits){
            if(!dailyHabit.getHabit().isCompleted()){ //인증을 하지 않았다면
                dailyHabit.getHabit().ResetDayStreak(false); //인증을 안했을 때 개별 습관 스트릭 초기화
            }
            dailyHabit.getHabit().CompletedUpdate(false); //전부 false로 바꿔서 인증버튼 활성화
        }
        //======================================================================일주일 습관 인증 여부 판단 (하루기준)
        List<WeeklyHabit> weeklyHabits = weekRepository.findAll().stream().toList();
        for(WeeklyHabit weeklyHabit : weeklyHabits){
            if(!weeklyHabit.getHabit().isCompleted()){
                weeklyHabit.getHabit().ResetDayStreak(false); //인증을 안했을 대 개별 습관 스트릭 초기화
            }
        }
        //======================================================================일주일 요일판단
        weekOfDay.WhatDay();
        //========================================================================인증한 습관 개수 초기화
    }
}