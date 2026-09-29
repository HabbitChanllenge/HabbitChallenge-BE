package org.example.saessakroutine.domain.habit.persistence.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class DailyHabitCreatRequest {
    private String name;
    private String periodType;
    private List<String> category = new ArrayList<>();
    private int totalRepeat;
}
