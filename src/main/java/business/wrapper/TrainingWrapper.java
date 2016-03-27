package business.wrapper;

import java.util.Calendar;

public class TrainingWrapper {

    private int courtId;
    
    private Calendar startTime;
    
    public TrainingWrapper() {
        
    }

    public TrainingWrapper(int courtId, Calendar startTime) {
        super();
        this.courtId = courtId;
        this.startTime = startTime;
    }

    public int getCourtId() {
        return courtId;
    }

    public void setCourtId(int courtId) {
        this.courtId = courtId;
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "TrainingWrapper [courtId=" + courtId + ", startTime=" + startTime + "]";
    }
    
}
