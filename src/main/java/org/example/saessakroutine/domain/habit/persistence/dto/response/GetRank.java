package org.example.saessakroutine.domain.habit.persistence.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.saessakroutine.user.entity.User;

@NoArgsConstructor
@Getter
public class GetRank {
    private int allStreak;
    private String userName;
    public GetRank(User user){
        this.allStreak = user.getAllStreak();
        this.userName = user.getUserId();
    }
}
