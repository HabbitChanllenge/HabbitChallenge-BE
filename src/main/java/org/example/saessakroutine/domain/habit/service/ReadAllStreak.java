package org.example.saessakroutine.domain.habit.service;

import lombok.RequiredArgsConstructor;
import org.example.saessakroutine.domain.habit.persistence.dto.response.GetAllStreakResponse;
import org.example.saessakroutine.user.entity.User;
import org.example.saessakroutine.user.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadAllStreak {
    private final UserRepository userRepository;
    public GetAllStreakResponse AllStreak(Authentication authentication){
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        return new GetAllStreakResponse(user.getAllStreak());
    }
}