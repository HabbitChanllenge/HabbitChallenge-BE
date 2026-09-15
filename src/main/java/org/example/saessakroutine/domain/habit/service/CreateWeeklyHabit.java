package org.example.saessakroutine.domain.habit.service;

import lombok.RequiredArgsConstructor;
import org.example.saessakroutine.domain.habit.persistence.dto.request.WeeklyHabitCreatRequest;
import org.example.saessakroutine.domain.habit.persistence.dto.status.exceptions.BadRequestException;
import org.example.saessakroutine.domain.entity.Habit;
import org.example.saessakroutine.domain.entity.WeeklyHabit;
import org.example.saessakroutine.domain.repository.HabitRepository;
import org.example.saessakroutine.domain.repository.WeekRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor //final속성들에게 생성자 만들어주는 어노테이션
public class CreateWeeklyHabit {
    private final WeekRepository weekRepository;
    private final HabitRepository habitRepository;

    @Transactional
    public String weeklyCreate(WeeklyHabitCreatRequest request){
        if(request.getHabitName().isBlank()){
            throw new BadRequestException("습관정보가 누락되었습니다.");
        }
        for(int i=0; i< request.getCategory().size(); i++){
            if(request.getCategory().get(i).isBlank()){
                throw new BadRequestException("습관정보가 누락되었습니다.");
            }
        }
        //=================================위에는 예외처리
        //=================================밑에는 습관 생성코드
        Habit habit = Habit.builder()
                .name(request.getHabitName())
                .periodType(request.getPeriodType())
                .category(request.getCategory())
                .build();
        habitRepository.save(habit);

        WeeklyHabit weeklyHabit = WeeklyHabit.builder()
                .habit(habit)
                .weekOfDay(request.getWeekOfDay())
                .build();
        weekRepository.save(weeklyHabit);
        weeklyHabit.CreateWeekCount(); //인증할 요일을 선택한 배열을 받아서 배열의 크기를 저장하는 메서드(스트릭을 계산할 때 사용하기 위해서)

        return "습관이 성공적으로 생성되었습니다.";
    }
}
