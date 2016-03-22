package business.wrapper;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AvailableTime {

    private int courtId;

    private Calendar time;

    public AvailableTime() {
    }

    public AvailableTime(int courtId, Calendar time) {
        this.courtId = courtId;
        this.time = time;
    }

    public int getCourtId() {
        return courtId;
    }

    public void setCourtId(int courtId) {
        this.courtId = courtId;
    }

    public Calendar getTime() {
        return time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }

    @Override
    public String toString() {
        String timeString = new SimpleDateFormat("HH:00").format(time.getTime());
        return "AvailableTime [courtId=" + courtId + ", time=" + timeString + "]";
    }

}
