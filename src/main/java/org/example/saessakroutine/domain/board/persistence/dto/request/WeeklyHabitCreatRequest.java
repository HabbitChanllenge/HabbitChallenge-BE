package org.example.saessakroutine.domain.board.persistence.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class WeeklyHabitCreatRequest {
    private String habitName;
    private String periodType;
    private List<String> category = new ArrayList<>();
    private List<Integer> weekOfDay = new ArrayList<>();
}
