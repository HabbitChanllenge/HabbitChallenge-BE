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
        List<GetRank> getRank = userRepository.findTop20ByOrderByAllStreakDesc().stream().map(GetRank::new).toList();
        for (int i=0; i < getRank.size(); i++){
            getRank.get(i).AddRank(i+1);
        }
        return getRank;
    }
}
