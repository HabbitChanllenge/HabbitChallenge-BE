package org.example.saessakroutine.domain.habit.service.count;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
@RequiredArgsConstructor
public class WhatDayOfWeek {
    Calendar calendar = Calendar.getInstance();
    int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

    private final WeekTime weekTime;
    private final DayOfWeekTime dayOfWeekTime;

    public void WhatDay(){
        if(dayOfWeek == 2){
            weekTime.NewWeek();
        } else {
            dayOfWeekTime.DayOfWeeks(dayOfWeek);
        }
    }
}
