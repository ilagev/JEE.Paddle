package data.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Authorization {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn
    private User user;

    @Enumerated(EnumType.STRING)
    private Role role;

    public Authorization() {
    }

    public Authorization(User user, Role role) {
        assert user != null && role != null;
        this.user = user;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Role getRole() {
        return role;
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
        return id == ((Authorization) obj).id;
    }

    @Override
    public String toString() {
        return "Authorization [id=" + id + ", userId=" + user.getId() + ", role=" + role + "]";
    }

}
