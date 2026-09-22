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
    private final WhatWeekOfDay weekOfDay;

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void NewDay(){
//        System.out.println("오류 없음!!");
        List<DailyHabit> dailyHabits = dayRepository.findAll().stream().toList(); //하루기준 습관만
        for(DailyHabit dailyHabit : dailyHabits){
            if(!dailyHabit.getHabit().isCompleted()){ //인증을 하지 않았다면
                if (dailyHabit.getHabit().getUser().isAllStreakCount()){ //전체스트릭 인증 판단이 참일 때만 바꾸기
                    dailyHabit.getHabit().getUser().updateAllStreakCount(false); //하나라도 인증을 안하면 전체 스트릭 초기화를 하기 위해 표시
                }
                dailyHabit.getHabit().ResetDayStreak(false); //인증을 안했을 때 개별 습관 스트릭 초기화
            }
            dailyHabit.getHabit().CompletedUpdate(false); //전부 false로 바꿔서 인증버튼 활성화
        }
        //======================================================================일주일 습관 인증 여부 판단 (하루기준)
        List<WeeklyHabit> weeklyHabits = weekRepository.findAll().stream().toList();
        for(WeeklyHabit weeklyHabit : weeklyHabits){
            if(!weeklyHabit.getHabit().isCompleted()){
                if(weeklyHabit.getHabit().getUser().isAllStreakCount()){
                    weeklyHabit.getHabit().getUser().updateAllStreak(false); //인증하지 않았을 때 전체 스트릭 초기화를 위해서
                }
                weeklyHabit.getHabit().ResetDayStreak(false); //인증을 안했을 대 개별 습관 스트릭 초기화
            }
        }
        //======================================================================일주일 요일판단
        weekOfDay.WhatDay();
        //=======================================================================전체 스트릭 판단

        List<User> users = userRepository.findAll().stream().toList();
        for(User user : users){
            user.updateAllStreak(user.isAllStreakCount());
            user.updateAllStreakCount(true); //기본 설정을 true로 해두고 인증하지 않은 경우 false로 바꿔서 전체 스트릭 초기화 되도록
        }
    }
}
