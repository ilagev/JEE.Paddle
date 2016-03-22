package data.entities;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Reserve {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn
    private Court court;

    @ManyToOne
    @JoinColumn
    private User user;

    private Calendar date;

    public Reserve(Court court, User user, Calendar date) {
        this.court = court;
        this.user = user;
        this.date = date;
    }

    public Reserve(Court court, Calendar date) {
        this(court, null, date);
    }

    public Reserve() {
    }

    public int getId() {
        return id;
    }

    public Court getCourt() {
        return court;
    }

    public Calendar getDate() {
        return date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return id == ((Reserve) obj).id;
    }

    @Override
    public String toString() {
        String time = new SimpleDateFormat("HH:00 dd-MMM-yyyy ").format(date.getTime());
        return "Reserve [id=" + id + ", courtId=" + court.getId() + ", date=" + time + "]";
    }

}
