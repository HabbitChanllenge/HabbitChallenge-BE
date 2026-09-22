package org.example.saessakroutine.domain.habit.service;

import lombok.RequiredArgsConstructor;
import org.example.saessakroutine.domain.habit.persistence.dto.response.GetAllStreak;
import org.example.saessakroutine.domain.habit.persistence.dto.status.exceptions.NoContentsException;
import org.example.saessakroutine.user.entity.User;
import org.example.saessakroutine.user.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadAllStreak {
    private final UserRepository userRepository;
    public GetAllStreak AllStreak(Authentication authentication){
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(()->new NoContentsException("존재하는 유저가 아닙니다."));
        return new GetAllStreak(user.getAllStreak());
    }
}