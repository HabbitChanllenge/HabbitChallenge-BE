package org.example.saessakroutine.domain.habit.service;

import lombok.RequiredArgsConstructor;
import org.example.saessakroutine.domain.habit.persistence.dto.response.GetRank;
import org.example.saessakroutine.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadRank {
    private final UserRepository userRepository;
    public List<GetRank> Ranking() {
        return userRepository.findTop20ByOrderByAllStreakDesc().stream().map(GetRank::new).toList();
        //20위까지 자르는 기준 설정해야한다.
    }
}
