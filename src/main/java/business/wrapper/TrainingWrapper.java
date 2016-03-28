package business.wrapper;

import java.util.Calendar;
import java.util.List;

public class TrainingWrapper {
    
    private int id;

    private int courtId;
    
    private String trainerUsername;
    
    private Calendar startTime;
    
    private List<Integer> traineesIDs;
    
    public TrainingWrapper() {
        
    }
    
    public TrainingWrapper(int id) {
        super();
        this.id = id;
    }

    public TrainingWrapper(int courtId, Calendar startTime) {
        super();
        this.courtId = courtId;
        this.startTime = startTime;
    }
    
    public TrainingWrapper(int id, int courtId, String trainerUsername, Calendar startTime) {
        super();
        this.id = id;
        this.courtId = courtId;
        this.trainerUsername = trainerUsername;
        this.startTime = startTime;
    }

    public TrainingWrapper(int id, int courtId, String trainerUsername, Calendar startTime, List<Integer> traineesIDs) {
        super();
        this.id = id;
        this.courtId = courtId;
        this.trainerUsername = trainerUsername;
        this.startTime = startTime;
        this.traineesIDs = traineesIDs;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTrainerUsername() {
        return trainerUsername;
    }

    public void setTrainerUsername(String trainerUsername) {
        this.trainerUsername = trainerUsername;
    }
    
    public List<Integer> getTraineesIDs() {
        return traineesIDs;
    }

    public void setTraineesIDs(List<Integer> traineesIDs) {
        this.traineesIDs = traineesIDs;
    }
    
}
