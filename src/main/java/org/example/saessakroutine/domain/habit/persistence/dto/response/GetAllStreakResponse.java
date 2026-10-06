package org.example.saessakroutine.domain.habit.persistence.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetAllStreakResponse {
    private int allStreak;
    public GetAllStreakResponse(int allStreak){
        this.allStreak = allStreak;
    }
}
