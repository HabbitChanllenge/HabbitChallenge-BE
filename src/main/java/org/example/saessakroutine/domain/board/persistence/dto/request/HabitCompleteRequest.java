package org.example.saessakroutine.domain.board.persistence.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HabitCompleteRequest {
    private int completedCount;
    //completedCount를 받아서 totalComplete와 비교를 해서 같으면 completed를 true로 바꿔서 저장
    // -> 하루인 경우, streak에 +1
}
