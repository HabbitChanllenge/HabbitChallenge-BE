package org.example.saessakroutine.domain.habit.service;

import lombok.RequiredArgsConstructor;
import org.example.saessakroutine.domain.habit.persistence.dto.response.GetAllHabitsResponse;
import org.example.saessakroutine.domain.habit.persistence.dto.status.exceptions.NoContentsException;
import org.example.saessakroutine.domain.entity.Habit;
import org.example.saessakroutine.user.entity.User;
import org.example.saessakroutine.user.repository.UserRepository;
import org.springframework.security.core.Authentication;
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
}