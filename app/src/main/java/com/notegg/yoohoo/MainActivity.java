package com.notegg.yoohoo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.notegg.yoohoo.calendar.CalcDateDiff;

import android.os.Bundle;

import com.notegg.yoohoo.calendar.CalendarPageAdapter;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 calendarView;
    private final CalcDateDiff cdd = new CalcDateDiff();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendarView = findViewById(R.id.calendarView);
        calendarView.setAdapter(new CalendarPageAdapter(this));

        Calendar c = Calendar.getInstance();
        int monthAfter = cdd.getMonthAfter1970(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1);
        if (monthAfter != -1) calendarView.setCurrentItem(monthAfter, false);
    }
}