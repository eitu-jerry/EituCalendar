package com.notegg.yoohoo.calendar;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class CalcDateDiff {

    public int getMonthAfter1970(int year, int month) {
        if (year < 1970) {
            return -1;
        }
        else if (month < 1 || month > 12) {
            return -1;
        }
        else {
            return (12 * (year - 1970)) + (month - 1);
        }
    }

    public List<CalendarCellItem> getDayList(int year, int month) {
        Log.d("getDayList", "Year: " + year + " Month: " + month);
        List<CalendarCellItem> list = new ArrayList<>();

        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getDefault());
        c.set(year, month - 1, 1, 0, 0, 0);

        //XXXX년 XX월 01일의 요일을 계산
        int dow = c.get(Calendar.DAY_OF_WEEK);
        Log.d("getDayList", "DayOfWeek: " + dow);

        //일요일이 아닌 경우 (달력 앞에 이전 달의 날짜가 표시 되는 경우)
        if (dow != 1) {
            //이전 달을 12월로 계산해야 하는 경우
            if (month == 1) {
                Log.d("getDayList", "Year: " + (year - 1) + " Month: " + 12);
                int dayInMonth = getDayInMonth(year - 1, 12);
                Log.d("getDayList", "dayInMonth: " + dayInMonth);
                for (int i=0; i<dow-1; i++) {
                    Calendar day = Calendar.getInstance();
                    day.setTimeZone(TimeZone.getDefault());
                    day.set(year - 1, 12, dayInMonth - i, 0, 0, 0);
                    list.add(0, new CalendarCellItem(day));
                }
            }
            //그 외
            else {
                Log.d("getDayList", "Year: " + year + " Month: " + (month - 1));
                int dayInMonth = getDayInMonth(year, month - 1);
                Log.d("getDayList", "dayInMonth: " + dayInMonth);
                for (int i=0; i<dow-1; i++) {
                    Calendar day = Calendar.getInstance();
                    day.setTimeZone(TimeZone.getDefault());
                    day.set(year, month - 2, dayInMonth - i, 0, 0, 0);
                    list.add(0, new CalendarCellItem(day));
                }
            }
        }

        //XXXX년 XX월 i일을 리스트에 추가
        int dayInMonth = getDayInMonth(year, month);
        for (int i=1; i<dayInMonth+1; i++) {
            Calendar day = Calendar.getInstance();
            day.setTimeZone(TimeZone.getDefault());
            day.set(year, month - 1, i, 0, 0, 0);
            list.add(new CalendarCellItem(day));
        }

//        //XXXX년 XX월의 마지막 날의 요일을 계산
//        c.set(year, month - 1, dayInMonth, 0, 0, 0);
//        dow = c.get(Calendar.DAY_OF_WEEK);
//
//        //토요일이 아닌 경우 (달력 뒤에 다음 달의 날짜가 표시 되는 경우)
//        if (dow != 7) {
//            //다음 달을 1월로 계산해야 하는 경우
//            if (month == 12) {
//                //for (int i=0; i<7-dow; i++) {
//                for (int i=0; i<42-list.size(); i++) {
//                    Calendar day = Calendar.getInstance();
//                    day.setTimeZone(TimeZone.getDefault());
//                    day.set(year + 1, 1, i + 1, 0, 0, 0);
//                    list.add(new CalendarCellItem(day));
//                }
//            }
//            //그 외
//            else {
//                //for (int i=0; i<7-dow; i++) {
//                for (int i=0; i<42-list.size(); i++) {
//                    Calendar day = Calendar.getInstance();
//                    day.setTimeZone(TimeZone.getDefault());
//                    day.set(year, month, i + 1, 0, 0, 0);
//                    list.add(new CalendarCellItem(day));
//                }
//            }
//        }

        Log.d("getDayList", "ListSize: " + list.size());
        int left = 42 - list.size();
        Log.d("getDayList", "Left: " + left);
        //다음 달을 1월로 계산해야 하는 경우
        if (month == 12) {
            //for (int i=0; i<7-dow; i++) {
            for (int i=0; i<left; i++) {
                Calendar day = Calendar.getInstance();
                day.setTimeZone(TimeZone.getDefault());
                day.set(year + 1, 1, i + 1, 0, 0, 0);
                list.add(new CalendarCellItem(day));
            }
        }
        //그 외
        else {
            //for (int i=0; i<7-dow; i++) {
            for (int i=0; i<left; i++) {
                Calendar day = Calendar.getInstance();
                day.setTimeZone(TimeZone.getDefault());
                day.set(year, month, i + 1, 0, 0, 0);
                list.add(new CalendarCellItem(day));
            }
        }

        Log.d("getDayList", "ListSize: " + list.size());
        return list;
    }

    public int getDayInMonth(int year, int month) {
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            return 31;
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            return 30;
        } else {
            if (year % 4 == 0) {
                if (year % 100 == 0) {
                    if (year % 400 == 0) return 29;
                    else return 28;
                }
                else return 29;
            }
            else return 28;
        }
    }

    public int getDayInYear(int year) {
        int day = 0;
        for (int i=1; i<13; i++) {
            day += getDayInMonth(year, i);
        }

        return day;
    }

    public int[] getDiff(String target, String today) {
        int targetYear = Integer.parseInt(target.substring(0,4));
        int targetMonth = Integer.parseInt(target.substring(5,7));
        int targetDay = Integer.parseInt(target.substring(8));
        int todayYear = Integer.parseInt(today.substring(0,4));
        int todayMonth = Integer.parseInt(today.substring(5,7));
        int todayDay = Integer.parseInt(today.substring(8));

        if (target.equals(today)) return new int[] {-1, 0, 0}; // 오늘이 디데이인 경우

        if (targetYear > todayYear) { // 연도가 다른 경우
            int yearDiff = targetYear - todayYear;
            if (yearDiff > 1) { // 연도로만 봤을 때 2년 이상 차이나는 경우
                if (targetMonth > todayMonth) { // 실제로 yearDiff 년 이상의 차이인 경우
                    int monthDiff = targetMonth - todayMonth;
                    if (targetDay >= todayDay) { // 날짜가 같거나 이후인 경우 ( yearDiff 년 + monthDiff 개월 + XX일 )
                        return new int[] {yearDiff, monthDiff, targetDay - todayDay};
                    }
                    else { // 날짜가 이전인 경우 ( yearDiff 년 + (monthDiff - 1) 개월 + XX일 )
                        int todayToMonthEnd = getDayInMonth(todayYear, todayMonth) - todayDay;
                        return new int[] {yearDiff, monthDiff - 1, todayToMonthEnd + targetDay};
                    }
                }
                else {
                    if (targetMonth == todayMonth) { // 같은 달인 경우
                        if (targetDay >= todayDay) { // 날짜가 같거나 이후인 경우 ( yearDiff 년 + 0개월 + XX일 )
                            return new int[] {yearDiff, 0, targetDay - todayDay};
                        }
                        else { // 날짜가 이전인 경우 ( (yearDiff - 1) 년 + 11개월 + XX일 )
                            int todayToMonthEnd = getDayInMonth(todayYear, todayMonth) - todayDay;
                            return  new int[] {yearDiff - 1, 11, todayToMonthEnd + targetDay};
                        }
                    }
                    else { // 서로 다른 달일 경우
                        int months = (12 - todayMonth) + (targetMonth - 1);
                        if (targetDay >= todayDay) { // 날짜가 같거나 이후인 경우 ( (yearDiff - 1) 년 + (months + 1) 개월 + XX일 )
                            return new int[] {yearDiff - 1, months + 1, targetDay - todayDay};
                        }
                        else { // 날짜가 이전인 경우 ( (yearDiff - 1) 년 + months 개월 + XX일 )
                            int todayToMonthEnd = getDayInMonth(todayYear, todayMonth) - todayDay;
                            return new int[] {yearDiff - 1, months, todayToMonthEnd + targetDay};
                        }
                    }
                }
            }
            else { // 연도로만 봤을 때 1년 차이인 경우
                if (targetMonth > todayMonth) { // 실제로 1년 이상의 차이인 경우
                    int monthDiff = targetMonth - todayMonth;
                    if (targetDay >= todayDay) { // 날짜가 같거나 이후인 경우 ( 1년 + monthDiff 개월 + XX일 )
                        return new int[] {1, monthDiff, targetDay - todayDay};
                    }
                    else { // 날짜가 이전인 경우 ( 1년 + (monthDiff - 1) 개월 + XX일 )
                        int todayToMonthEnd = getDayInMonth(todayYear, todayMonth) - todayDay;
                        return new int[] {1, monthDiff - 1, todayToMonthEnd + targetDay};
                    }
                }
                else {
                    if (targetMonth == todayMonth) { // 같은 달인 경우
                        if (targetDay >= todayDay) { // 날짜가 같거나 이후인 경우 ( 1년 + 0개월 + XX일 )
                            return new int[] {1, 0, targetDay - todayDay};
                        }
                        else { // 날짜가 이전인 경우 ( 0년 + 11개월 + XX일 )
                            int todayToMonthEnd = getDayInMonth(todayYear, todayMonth) - todayDay;
                            return  new int[] {0, 11, todayToMonthEnd + targetDay};
                        }
                    }
                    else { // 서로 다른 달일 경우
                        int months = (12 - todayMonth) + (targetMonth - 1);
                        if (targetDay >= todayDay) { // 날짜가 같거나 이후인 경우 ( 0년 + (months + 1) 개월 + XX일 )
                            return new int[] {0, months + 1, targetDay - todayDay};
                        }
                        else { // 날짜가 이전인 경우 ( 0년 + months 개월 + XX일 )
                            int todayToMonthEnd = getDayInMonth(todayYear, todayMonth) - todayDay;
                            return new int[] {0, months, todayToMonthEnd + targetDay};
                        }
                    }
                }
            }
        }
        else { // 연도가 같은 경우
            if (targetMonth > todayMonth) { // 달이 다른 경우
                int monthDiff = targetMonth - todayMonth;
                if (monthDiff > 1) { // 달로만 봤을 때 2달 이상 차이나는 경우
                    if (targetDay >= todayDay) { // 날짜가 같거나 이후인 경우 ( monthDiff 개월 + XX일 )
                        return new int[] {0, monthDiff, targetDay - todayDay};
                    }
                    else { // 날짜가 이전인 경우 ( (monthDiff-1) 개월 + XX일 )
                        int todayToMonthEnd = getDayInMonth(todayYear, todayMonth) - todayDay;
                        return new int[] {0, monthDiff - 1, todayToMonthEnd + targetDay};
                    }
                }
                else { // 달로만 봤을 때 한달 차이인 경우
                    if (targetDay >= todayDay) { // 날짜가 같거나 이후인 경우 ( 1개월 + XX일 )
                        return new int[] {0, 1, targetDay - todayDay};
                    }
                    else { // 날짜가 이전인 경우 ( 한달이 안되는 XX일 )
                        int todayToMonthEnd = getDayInMonth(todayYear, todayMonth) - todayDay;
                        return new int[] {0, 0, todayToMonthEnd + targetDay};
                    }
                }
            }
            else { // 같은 년도 같은 달인 경우 ( XX일 )
                return new int[] {0, 0, targetDay - todayDay};
            }
        }
    }
}
