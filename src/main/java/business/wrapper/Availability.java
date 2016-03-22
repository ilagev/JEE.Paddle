package business.wrapper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class Availability {

    private Calendar day;

    private List<AvailableTime> times;

    public Availability() {
    }

    public Availability(Calendar day, List<AvailableTime> times) {
        this.day = day;
        this.times = times;
    }

    public Availability(Calendar day, Map<Integer, List<Integer>> allTimesAvailable) {
        this.day = day;
        List<AvailableTime> times = new ArrayList<>();
        for (Integer court : allTimesAvailable.keySet()) {
            for (Integer hour : allTimesAvailable.get(court)) {
                Calendar hourCalendar = (Calendar) day.clone();
                hourCalendar.set(Calendar.HOUR_OF_DAY, hour);
                times.add(new AvailableTime(court, hourCalendar));
            }
        }
        this.times = times;
    }

    public Calendar getDay() {
        return day;
    }

    public void setDay(Calendar day) {
        this.day = day;
    }

    public List<AvailableTime> getTimes() {
        return times;
    }

    public void setTimes(List<AvailableTime> times) {
        this.times = times;
    }

    @Override
    public String toString() {
        String dayString = new SimpleDateFormat("dd-MMM-yyyy HH:mm").format(day.getTime());
        return "Availability [day=" + dayString + ", times=" + times + "]";
    }

}
