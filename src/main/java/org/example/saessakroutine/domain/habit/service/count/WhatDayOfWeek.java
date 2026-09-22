package org.example.saessakroutine.domain.habit.service.count;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
@RequiredArgsConstructor
public class WhatDayOfWeek {
    Calendar calendar = Calendar.getInstance();
    int weekOfDay = calendar.get(Calendar.DAY_OF_WEEK);
//    int weekOfDay = 3; //테스트용 코드

    private final WeekTime weekTime;
    private final DayOfWeekTime dayOfWeekTime;

    public void WhatDay(){
        if(weekOfDay == 2){
            weekTime.NewWeek();
        } else {
            dayOfWeekTime.DayOfWeeks(weekOfDay);
        }
    }
}
