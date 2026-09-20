package org.example.saessakroutine.domain.habit.service.count;

import org.example.saessakroutine.domain.entity.WeeklyHabit;
import org.example.saessakroutine.domain.repository.WeekRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class WeekTime {
    WeekRepository weekRepository;

    @Transactional
    public void NewWeek(){
        List<WeeklyHabit> weeklyHabits = weekRepository.findAll().stream().filter(weeklyHabit1 -> weeklyHabit1.getWeekOfDay().contains(1)).toList();
        List<WeeklyHabit> notWeeklyHabit = weekRepository.findAll().stream().filter(weeklyHabit1 -> !weeklyHabit1.getWeekOfDay().contains(1)).toList();
        for(WeeklyHabit weeklyHabit : weeklyHabits){
            weeklyHabit.getHabit().CompletedUpdate(false); //설정한 요일이 일치하면 버튼 활성화하기 위해서
            weeklyHabit.getHabit().getWeeklyHabit().NewWeekCount(); //일주일 달성 횟수 세는 필드 초기화(countWeek)
            if(weeklyHabit.getHabit().getWeeklyHabit().isChangeWeekOfDay()){ //일주일 변경사항이 있다면
             weeklyHabit.getHabit().getWeeklyHabit().HabitUpdate_Week_Real();
            }
        }
        //=========================================================일주일 습관 스트릭 계산
        for(WeeklyHabit weeklyHabit : notWeeklyHabit){
            weeklyHabit.getHabit().getWeeklyHabit().NewWeekCount();
            if(weeklyHabit.getHabit().getWeeklyHabit().getCountWeek() == weeklyHabit.getHabit().getWeeklyHabit().getStandardWeek()) {
                weeklyHabit.getHabit().HabitStreak(true); //일주일동안 해야하는 요일에 모두 인증을 마쳤다면 스트릭 증가
            } else {
                weeklyHabit.getHabit().HabitStreak(false); //completed의 상태가 false라는 것은 인증을 안했다는 것 이므로 스트릭 초기화

                if(weeklyHabit.getHabit().getUser().isAllStreakCount()){
                    weeklyHabit.getHabit().getUser().updateAllStreakCount(false); //하나라도 인증을 안하면 전체 스트릭 초기화를 하기 위해 표시
                }

            }
            if(weeklyHabit.getHabit().getWeeklyHabit().isChangeWeekOfDay()){ //일주일 변경사항이 있다면
                weeklyHabit.getHabit().getWeeklyHabit().HabitUpdate_Week_Real();
            }
        }
    }
}