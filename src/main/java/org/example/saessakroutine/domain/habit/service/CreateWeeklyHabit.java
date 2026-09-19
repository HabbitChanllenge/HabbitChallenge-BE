package org.example.saessakroutine.domain.habit.service;

import lombok.RequiredArgsConstructor;
import org.example.saessakroutine.domain.habit.persistence.dto.request.WeeklyHabitCreatRequest;
import org.example.saessakroutine.domain.habit.persistence.dto.status.exceptions.BadRequestException;
import org.example.saessakroutine.domain.entity.Habit;
import org.example.saessakroutine.domain.entity.WeeklyHabit;
import org.example.saessakroutine.domain.habit.persistence.dto.status.exceptions.NoContentsException;
import org.example.saessakroutine.domain.repository.HabitRepository;
import org.example.saessakroutine.domain.repository.WeekRepository;
import org.example.saessakroutine.user.entity.User;
import org.example.saessakroutine.user.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;

@Service
@RequiredArgsConstructor //final속성들에게 생성자 만들어주는 어노테이션
public class CreateWeeklyHabit {
    private final WeekRepository weekRepository;
    private final HabitRepository habitRepository;
    private final UserRepository userRepository;

    Calendar calendar = Calendar.getInstance();
    int weekOfDay = calendar.get(Calendar.DAY_OF_WEEK);

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(()->new NoContentsException("유저가 존재하지 않습니다."));

        Habit habit = Habit.builder()
                .name(request.getHabitName())
                .periodType(request.getPeriodType())
                .category(request.getCategory())
                .user(user)
                .build();
        habitRepository.save(habit);

        WeeklyHabit weeklyHabit = WeeklyHabit.builder()
                .habit(habit)
                .weekOfDay(request.getWeekOfDay())
                .build();
        weeklyHabit.CreateWeekCount(); //인증할 요일을 선택한 배열을 받아서 배열의 크기를 저장하는 메서드(스트릭을 계산할 때 사용하기 위해서)
        weekRepository.save(weeklyHabit);

        for(int i = 0; i < weeklyHabit.getWeekOfDay().size(); i++){
            if(weeklyHabit.getWeekOfDay().get(i).equals(weekOfDay)){
                weeklyHabit.getHabit().CompletedUpdate(false); //생성한 습관이 생성한 날의 요일이라면 인증 활성화 아니라면 true로 막아두기
            }
        }


        return "습관이 성공적으로 생성되었습니다.";
    }
}
