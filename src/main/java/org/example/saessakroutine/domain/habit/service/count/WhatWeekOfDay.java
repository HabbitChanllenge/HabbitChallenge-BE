package org.example.saessakroutine.domain.habit.service.count;

import java.util.Calendar;

public class WhatWeekOfDay {
    Calendar calendar = Calendar.getInstance();
    int weekOfDay = calendar.get(Calendar.DAY_OF_WEEK);

    WeekTime weekTime;
    WeekOfDayTime weekOfDayTime;

    public void WhatDay(){
        switch (weekOfDay){
            case 1:
                weekTime.NewWeek(); //월요일 0시 -> 새로운 일주일 시작
                break;
            case 2:
                weekOfDayTime.DayOfWeeks(2);
                break;
            case 3:
                weekOfDayTime.DayOfWeeks(3);
                break;
            case 4:
                weekOfDayTime.DayOfWeeks(4);
                break;
            case 5:
                weekOfDayTime.DayOfWeeks(5);
                break;
            case 6:
                weekOfDayTime.DayOfWeeks(6);
                break;
            default:
                weekOfDayTime.DayOfWeeks(0); //일요일
                break;
        }
    }
}
