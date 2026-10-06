package org.example.saessakroutine.domain.habit.persistence.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class WeeklyHabitCreatRequest {
    private String name;
    private String periodType;
    private List<String> categories = new ArrayList<>();
    private List<Integer> dayOfWeek = new ArrayList<>();
}
