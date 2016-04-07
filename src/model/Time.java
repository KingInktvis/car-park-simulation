package model;

/**
 * Created by rik on 4/7/16.
 */
public class Time {

    private int day = 0;
    private int hour = 0;
    private int minute = 0;

    public Time(){}

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public void dayIncrement(){
        day++;
        checkTime();
    }

    public void hourIncrement(){
        hour++;
        checkTime();
    }

    public void minuteIncrement(){
        minute++;
        checkTime();
    }

    public void checkTime(){
        while (minute > 59) {
            minute -= 60;
            hour++;
        }
        while (hour > 23) {
            hour -= 24;
            day++;
        }
        while (day > 6) {
            day -= 7;
        }
    }
}
