package org.example.saessakroutine.domain.habit.service;

import lombok.RequiredArgsConstructor;
import org.example.saessakroutine.domain.habit.persistence.dto.request.DailyHabitCreatRequest;
import org.example.saessakroutine.domain.habit.persistence.dto.status.exceptions.BadRequestException;
import org.example.saessakroutine.domain.entity.DailyHabit;
import org.example.saessakroutine.domain.entity.Habit;
import org.example.saessakroutine.domain.habit.persistence.dto.status.exceptions.NoContentsException;
import org.example.saessakroutine.domain.repository.DayRepository;
import org.example.saessakroutine.domain.repository.HabitRepository;
import org.example.saessakroutine.user.entity.User;
import org.example.saessakroutine.user.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor //final인 속성에 전부 생성자를 생성해준다.
public class CreateDailyHabit {
    private final DayRepository dayRepository;
    private final HabitRepository habitRepository;
    private final UserRepository userRepository;

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(()->new NoContentsException("유저가 존재하지 않습니다."));

        Habit habit = Habit.builder()
                .name(request.getHabitName())
                .periodType(request.getPeriodType())
                .category(request.getCategory())
                .user(user)
                .completed(false)
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
