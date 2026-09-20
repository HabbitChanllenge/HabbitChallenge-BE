package org.example.saessakroutine.domain.habit.service.count;

import java.util.Calendar;

public class WhatWeekOfDay {
    Calendar calendar = Calendar.getInstance();
//    int weekOfDay = calendar.get(Calendar.DAY_OF_WEEK);
    int weekOfDay = 1;

    WeekTime weekTime = new WeekTime();
    WeekOfDayTime weekOfDayTime = new WeekOfDayTime();

    public void WhatDay(){
        if(weekOfDay == 2){
            weekTime.NewWeek();
        } else {
            weekOfDayTime.DayOfWeeks(weekOfDay);
        }
    }
}
