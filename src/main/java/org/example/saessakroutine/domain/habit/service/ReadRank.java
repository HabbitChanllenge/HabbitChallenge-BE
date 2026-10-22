package org.example.saessakroutine.domain.habit.service;

import lombok.RequiredArgsConstructor;
import org.example.saessakroutine.domain.habit.persistence.dto.response.GetRankResponse;
import org.example.saessakroutine.domain.habit.persistence.dto.status.exceptions.NoContentsException;
import org.example.saessakroutine.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadRank {
    private final UserRepository userRepository;
    public List<GetRankResponse> Ranking() {
        List<GetRankResponse> getRankResponses = userRepository.findTop20ByOrderByAllStreakDesc().stream().map(GetRankResponse::new).toList();
        if (getRankResponses.isEmpty()){ //비어있는지 검사?
            throw new NoContentsException();
        } else {
            for (int i = 0; i < getRankResponses.size(); i++){
                getRankResponses.get(i).AddRank(i+1);
            }
        }
        return getRankResponses;
    }
}
