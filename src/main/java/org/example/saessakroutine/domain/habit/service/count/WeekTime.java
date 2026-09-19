package org.example.saessakroutine.domain.habit.service.count;

import lombok.RequiredArgsConstructor;
import org.example.saessakroutine.domain.entity.Habit;
import org.example.saessakroutine.domain.entity.WeeklyHabit;
import org.example.saessakroutine.domain.repository.HabitRepository;
import org.example.saessakroutine.domain.repository.WeekRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor //final속성들에게 생성자 만들어주는 어노테이션
public class WeekTime {
    private final WeekRepository weekRepository;
    private final HabitRepository habitRepository;

    @Transactional
    public void NewWeek(){
        List<WeeklyHabit> weeklyHabit = weekRepository.findAll().stream().filter(weeklyHabit1 -> weeklyHabit1.getWeekOfDay().contains(1)).toList();
        List<WeeklyHabit> notWeeklyHabit = weekRepository.findAll().stream().filter(weeklyHabit1 -> !weeklyHabit1.getWeekOfDay().contains(1)).toList();
        for(int i=0; i< weeklyHabit.size(); i++){
            Habit habit = weeklyHabit.get(i).getHabit();
            habit.CompletedUpdate(false); //설정한 요일이 일치하면 버튼 활성화하기 위해서
            habit.getWeeklyHabit().NewWeekCount(); //일주일 달성 횟수 세는 필드 초기화(countWeek)
            if(habit.getWeeklyHabit().isChangeWeekOfDay()){ //일주일 변경사항이 있다면
             habit.getWeeklyHabit().HabitUpdate_Week_Real();
            }
            habitRepository.save(habit);
        }
        //=========================================================일주일 습관 스트릭 계산
        for(int i=0; i<notWeeklyHabit.size(); i++){
            Habit notHabit = notWeeklyHabit.get(i).getHabit();
            notHabit.getWeeklyHabit().NewWeekCount();
            if(notHabit.getWeeklyHabit().getCountWeek() == notHabit.getWeeklyHabit().getStandardWeek()) {
                notHabit.HabitStreak(true); //일주일동안 해야하는 요일에 모두 인증을 마쳤다면 스트릭 증가
            } else {
                notHabit.HabitStreak(false); //completed의 상태가 false라는 것은 인증을 안했다는 것 이므로 스트릭 초기화

                if(notHabit.getUser().isAllStreakCount()){
                    notHabit.getUser().updateAllStreakCount(false); //하나라도 인증을 안하면 전체 스트릭 초기화를 하기 위해 표시
                }

            }
            if(notHabit.getWeeklyHabit().isChangeWeekOfDay()){ //일주일 변경사항이 있다면
                notHabit.getWeeklyHabit().HabitUpdate_Week_Real();
            }
            habitRepository.save(notHabit);
        }
    }
}