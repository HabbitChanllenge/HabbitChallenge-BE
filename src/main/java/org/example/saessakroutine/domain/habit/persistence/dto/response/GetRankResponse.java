package org.example.saessakroutine.domain.habit.persistence.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.saessakroutine.user.entity.User;

@NoArgsConstructor
@Getter
public class GetRankResponse {
    private int allStreak;
    private String userName;
    private int rank;
    public GetRankResponse(User user){
        this.allStreak = user.getAllStreak();
        this.userName = user.getUserId();
    }
    public void AddRank(int rank){
        this.rank = rank;
    }
}
