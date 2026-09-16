package org.example.saessakroutine.domain.habit.service.count;

import lombok.RequiredArgsConstructor;
import org.example.saessakroutine.domain.entity.Habit;
import org.example.saessakroutine.domain.entity.WeeklyHabit;
import org.example.saessakroutine.domain.repository.HabitRepository;
import org.example.saessakroutine.domain.repository.WeekRepository;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@RequiredArgsConstructor //final속성들에게 생성자 만들어주는 어노테이션
public class WeekTime {
    private final HabitRepository habitRepository;
    private final WeekRepository weekRepository;

    @Scheduled(cron = "0 1 0 * * *")
    public void NewWeek(){
        List<WeeklyHabit> weeklyHabit = weekRepository.findAll().stream().filter(weeklyHabit1 -> weeklyHabit1.getWeekOfDay().contains(1)).toList();
        List<WeeklyHabit> notWeeklyHabit = weekRepository.findAll().stream().filter(weeklyHabit1 -> !weeklyHabit1.getWeekOfDay().contains(1)).toList();
        for(int i=0; i< weeklyHabit.size(); i++){
            Habit habit = habitRepository.getReferenceById(weeklyHabit.get(i).getHabit().getHabit_id());
            habit.WeekCompleted(false); //설정한 요일이 일치하면 버튼 활성화하기 위해서
            habit.getWeeklyHabit().NewWeekCount();
            if(habit.getWeeklyHabit().isChangeWeekOfDay()){ //일주일 변경사항이 있다면
             habit.getWeeklyHabit().HabitUpdate_Week_Real();
            }
        }
        for(int i=0; i<notWeeklyHabit.size(); i++){
            Habit notHabit = habitRepository.getReferenceById(notWeeklyHabit.get(i).getHabit().getHabit_id());
            notHabit.getWeeklyHabit().NewWeekCount();
            if(notHabit.getWeeklyHabit().getCountWeek() == notHabit.getWeeklyHabit().getStandardWeek()) {
                notHabit.WeekStreak(true); //일주일동안 해야하는 요일에 모두 인증을 마쳤다면 스트릭 증가
            } else {
                notHabit.WeekStreak(false); //completed의 상태가 false라는 것은 인증을 안했다는 것 이므로 스트릭 초기화
                notHabit.getUser().updateAllStreak(0); //무엇 하나라도 인증 실패한다면 전체 스트릭 초기화
            }
            if(notHabit.getWeeklyHabit().isChangeWeekOfDay()){ //일주일 변경사항이 있다면
                notHabit.getWeeklyHabit().HabitUpdate_Week_Real();
            }
        }
    }
}