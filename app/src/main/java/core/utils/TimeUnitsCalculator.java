package core.utils;

public class TimeUnitsCalculator {
    private int seconds;
    private int minutes;
    private int hours;
    private int days;

    public TimeUnitsCalculator(long duration) {
        seconds = (int) duration % 60;
        minutes = (int) (duration / 60) % 60;
        hours = (int) (duration / 3600) % 24;
        days = (int) (duration / (24*3600));
    }

    public int getSeconds() {
        return seconds;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getHours() {
        return hours;
    }

    public int getDays() {
        return days;
    }
}
