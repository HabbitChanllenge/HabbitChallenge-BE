package org.example.saessakroutine.domain.habit.persistence.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetAllStreak {
    private int allStreak;
    public GetAllStreak(int allStreak){
        this.allStreak = allStreak;
    }
}
