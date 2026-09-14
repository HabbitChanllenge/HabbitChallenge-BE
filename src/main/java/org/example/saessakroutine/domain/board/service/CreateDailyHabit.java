package org.example.saessakroutine.domain.board.service;

import lombok.RequiredArgsConstructor;
import org.example.saessakroutine.domain.board.persistence.dto.request.DailyHabitCreatRequest;
import org.example.saessakroutine.domain.board.persistence.dto.status.exceptions.BadRequestException;
import org.example.saessakroutine.domain.entity.DailyHabit;
import org.example.saessakroutine.domain.entity.Habit;
import org.example.saessakroutine.domain.repository.DayRepository;
import org.example.saessakroutine.domain.repository.HabitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor //final인 속성에 전부 생성자를 생성해준다.
public class CreateDailyHabit {
    private final DayRepository dayRepository;
    private final HabitRepository habitRepository;

    @Transactional
    public String dalyCreate(DailyHabitCreatRequest request){
        if(request.getHabitName().isBlank()){
            throw new BadRequestException("습관정보가 누락되었습니다.");
        }
        for(int i=0; i< request.getCategory().size(); i++){
            if(request.getCategory().get(i).isBlank()){
                throw new BadRequestException("습관정보가 누락되었습니다.");
            }
        }
        //========================================예외처리
        if (request.getTotalRepeat() == 0){
            throw new BadRequestException("습관정보가 누락되었습니다.");
        }

        //======================================== 예외처리
        Habit habit = Habit.builder()
                .name(request.getHabitName())
                .periodType(request.getPeriodType())
                .category(request.getCategory())
                .build();
        habitRepository.save(habit);

        DailyHabit dalyHabit = DailyHabit.builder()
                .habit(habit)
                .totalRepeat(request.getTotalRepeat())
                .build();
        dayRepository.save(dalyHabit);
        return "습관이 성공적으로 생성되었습니다.";
    }
}
