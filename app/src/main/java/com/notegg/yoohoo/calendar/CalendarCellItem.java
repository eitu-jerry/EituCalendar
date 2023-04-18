package com.notegg.yoohoo.calendar;

import java.util.Calendar;

public class CalendarCellItem {

    private Calendar day;

    public CalendarCellItem(Calendar day) {
        this.day = day;
    }

    public Calendar getDay() {
        return day;
    }

    public void setDay(Calendar day) {
        this.day = day;
    }

}
