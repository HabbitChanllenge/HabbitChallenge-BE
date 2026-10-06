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
    int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

    @Transactional
    public int weeklyCreate(WeeklyHabitCreatRequest request, Authentication authentication){
        if(request.getName().isEmpty()||request.getName().isBlank()){
            throw new BadRequestException();
        }
        for(int i=0; i< request.getCategories().size(); i++){
            if(request.getCategories().get(i).isBlank()){
                throw new BadRequestException();
            }
        }
        //=================================위에는 예외처리
        //=================================밑에는 습관 생성코드
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(NoContentsException::new);

        Habit habit = Habit.builder()
                .name(request.getName())
                .periodType(request.getPeriodType())
                .category(request.getCategories())
                .user(user)
                .completed(true) //일단 생성 할때는 버튼을 막아 두었다가 밑에서 for문으로 해당하는 오늘이 해당하는 요일인지 판단하고 false로 바꾸어 버튼을 활성화 시킨다.
                .build();
        user.updateAllHabits(true);
        habitRepository.save(habit);

        WeeklyHabit weeklyHabit = WeeklyHabit.builder()
                .habit(habit)
                .dayOfWeek(request.getDayOfWeek())
                .build();
        weeklyHabit.CreateWeekCount(); //인증할 요일을 선택한 배열을 받아서 배열의 크기를 저장하는 메서드(스트릭을 계산할 때 사용하기 위해서)

        for(int i = 0; i < weeklyHabit.getDayOfWeek().size(); i++){
            if(weeklyHabit.getDayOfWeek().get(i).equals(dayOfWeek)){
                weeklyHabit.getHabit().CompletedUpdate(false); //생성한 습관이 생성한 날의 요일이라면 인증 활성화 아니라면 true로 막아두기
            }
        }
        weekRepository.save(weeklyHabit);


        return 201;
    }
}
