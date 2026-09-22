package org.example.saessakroutine.domain.habit.persistence.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class HabitsPatchRequest {
    private String name;
    private List<String> category = new ArrayList<>();
    private Integer totalRepeat;
    private List<Integer> WeekOfDay = new ArrayList<>();
}
