package data.entities;

import java.util.Calendar;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Training {
    
    @Id
    @GeneratedValue
    private int id;
    
    @ManyToOne
    @JoinColumn
    private Court court;
    
    @ManyToOne
    @JoinColumn
    private User trainer;
    
    @ManyToMany
    @JoinColumn
    private Set<User> trainees;
    
    @Column(unique = false, nullable = false)
    private Calendar startTime;
    
    @Column(unique = false, nullable = false)
    private Calendar endTime;
    
    public Training() {
    }
    
    public Training(int id, Court court, User trainer, Set<User> trainees, Calendar startTime, Calendar endTime) {
        super();
        this.id = id;
        this.court = court;
        this.trainer = trainer;
        this.trainees = trainees;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Court getCourt() {
        return court;
    }

    public void setCourt(Court court) {
        this.court = court;
    }

    public User getTrainer() {
        return trainer;
    }

    public void setTrainer(User trainer) {
        this.trainer = trainer;
    }

    public Set<User> getTrainees() {
        return trainees;
    }

    public void setTrainees(Set<User> trainees) {
        this.trainees = trainees;
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Training [id=" + id + ", court=" + court + ", trainer=" + trainer + ", trainees=" + trainees + ", startTime=" + startTime
                + ", endTime=" + endTime + "]";
    }
    
    @Override
    public int hashCode() {
        return id;
    }

}
