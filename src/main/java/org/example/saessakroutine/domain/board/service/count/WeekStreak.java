package org.example.saessakroutine.domain.board.service.count;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.saessakroutine.domain.entity.WeeklyHabit;
import org.example.saessakroutine.domain.repository.HabitRepository;
import org.example.saessakroutine.domain.repository.WeekRepository;
import org.springframework.stereotype.Service;

@Getter
@RequiredArgsConstructor
@Service
public class WeekStreak {
    private final HabitRepository habitRepository;
    private final WeekRepository weekRepository;

     public void CountWeek(Long id, boolean beforeCompleted, boolean newCompleted){
        WeeklyHabit weeklyHabit = weekRepository.findById(id).orElseThrow(()->new IllegalArgumentException("스트릭을 갱신할 습관이 없습니다."));
        if(beforeCompleted){
            if (!newCompleted){
                weeklyHabit.SubtractStreak(); //인증이 완료된 상태였는데 인증변경으로 streak이 원상복구됨.
            } //새로운 요청이 들어왔음에도 여전히 인증 완료 상태일 경우, 값 변경 없음.
        } else {
            if(newCompleted){
                weeklyHabit.AddWeekStreak(); //인증이 완료되지 않은 상태였는데 인증으로 streak이 추가됨.
            } //그 외의 상황은 변경은 되었으나, 인증이 완료되지 않아 streak이 오르지 않음.
        }
    }
}
