package org.example.saessakroutine.domain.habit.service;

import lombok.RequiredArgsConstructor;
import org.example.saessakroutine.domain.habit.persistence.dto.response.GetAllHabitsResponse;
import org.example.saessakroutine.domain.habit.persistence.dto.status.exceptions.NoContentsException;
import org.example.saessakroutine.domain.entity.Habit;
import org.example.saessakroutine.user.entity.User;
import org.example.saessakroutine.user.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ReadAllHabits {
    private final UserRepository userRepository;
    @Transactional
    public List<GetAllHabitsResponse> ReadAllHabit(Authentication authentication){
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(()->new NoContentsException("유저가 존재하지 않습니다."));

        List<Habit> allHabitResponserList = user.getHabit().stream().toList();
        return allHabitResponserList.stream().map(GetAllHabitsResponse::new).toList();
    }
//    private final HabitRepository habitRepository;
//    public List<GetAllHabitsResponse> ReadAll(){ //전체 습관 불러오는 Response로 반환 형식 선언
//        List<Habit> allHabitsResponseList = habitRepository.findAll();
//        if (allHabitsResponseList.isEmpty()){
//            throw new NoContentsException("조회할 습관이 없습니다."); //사용자 지정 예외
//        }
//        return allHabitsResponseList.stream().map(GetAllHabitsResponse::new).toList();
//    }
}