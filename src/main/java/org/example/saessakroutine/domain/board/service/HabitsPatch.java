package org.example.saessakroutine.domain.board.service;

import lombok.RequiredArgsConstructor;
import org.example.saessakroutine.domain.board.persistence.dto.request.HabitsPatchRequest;
import org.example.saessakroutine.domain.board.persistence.dto.status.exceptions.BadRequestException;
import org.example.saessakroutine.domain.board.persistence.dto.status.exceptions.NotThingException;
import org.example.saessakroutine.domain.entity.DailyHabit;
import org.example.saessakroutine.domain.entity.Habit;
import org.example.saessakroutine.domain.entity.WeeklyHabit;
import org.example.saessakroutine.domain.repository.DayRepository;
import org.example.saessakroutine.domain.repository.HabitRepository;
import org.example.saessakroutine.domain.repository.WeekRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HabitsPatch {
    private final HabitRepository habitRepository;
    private final DayRepository dayRepository;
    private final WeekRepository weekRepository;

    public String Patch(Long id, HabitsPatchRequest request){
        Habit habit = habitRepository.findById(id).orElseThrow(() -> new NotThingException("수정할 습관이 없습니다."));


        //============================================================== 습관 카테고리 수정 (Habit)

        if(request.getCategory() != null){
            habit.UpdateHabits_category(request.getCategory());
            habitRepository.save(habit);
        }

        //=============================================================== 습관 이름 수정 (Habit)

        if(request.getName() != null){
            if(request.getName().isBlank()){
                throw new BadRequestException("습관의 정보가 누락되었습니다.");
            }
            habit.UpdateHabits_name(request.getName());
            habitRepository.save(habit);
        }

        //=============================================================== 습관 총 달성 횟수 수정 (Daily)

        if(request.getTotalRepeat() != null){
            DailyHabit dailyHabit = dayRepository.findById(id).orElseThrow(()->new NotThingException("수정할 습관이 없습니다.(Daily)"));
            if(request.getTotalRepeat() == 0){
                throw new BadRequestException("습관의 정보가 누락되었습니다.");
            }
            habit.CompleteUpdateDay(0, habit.isCompleted());
            //습관을 완료한 경우라면 습관 인증 페이지가 열리지 않으므로 다음날부터 수정된 횟수로 할 수 있게 하기 위해 완료한 습관이라면 true가 들어가게 되고,
            //완료한 습관이 아닐 경우, 변경한 목표 습관 달성 횟수가 현재 습관 달성 횟수보다 많은 경우를 대비하여 0으로 초기화한다.
            dailyHabit.HabitUpdate_day(request.getTotalRepeat());
            dayRepository.save(dailyHabit);
            habitRepository.save(habit);
        }

        //=============================================================== 습관 달성 요일 수정 (Weekly)

        if(request.getWeekOfDay() != null){
            WeeklyHabit weeklyHabit = weekRepository.findById(id).orElseThrow(()->new NotThingException("수정할 습관이 업습니다.(Weekly)"));
            weeklyHabit.HabitUpdate_Week(request.getWeekOfDay(), true); //여기서 true가 된다면, 월요일 0시가 되었을 때 바꾼다.
            weekRepository.save(weeklyHabit);
        } else if(habit.getPeriodType().equals("WEEKLY")){
            throw new BadRequestException("습관의 정보가 누락되었습니다.");
        }
        return "습관이 성공적으로 수정되었습니다.";
    }
}
