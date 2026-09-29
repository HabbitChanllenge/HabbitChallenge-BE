package org.example.saessakroutine.domain.habit.service;

import lombok.RequiredArgsConstructor;
import org.example.saessakroutine.domain.habit.persistence.dto.response.GetRank;
import org.example.saessakroutine.domain.habit.persistence.dto.status.exceptions.NoContentsException;
import org.example.saessakroutine.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadRank {
    private final UserRepository userRepository;
    public List<GetRank> Ranking() {
        List<GetRank> getRank = userRepository.findTop20ByOrderByAllStreakDesc().stream().map(GetRank::new).toList();
        if (getRank.isEmpty()){ //비어있는지 검사?
            throw new NoContentsException();
        } else {
            for (int i=0; i < getRank.size(); i++){
                getRank.get(i).AddRank(i+1);
            }
        }
        return getRank;
    }
}
