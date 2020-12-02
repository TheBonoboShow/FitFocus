package be.spacedandy.FitFocus.models;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class SubscriptionType {

    @Id
    @GeneratedValue
    private int id;
    @NonNull
    private String name;
    @NonNull
    private int numberOfSessions;
    @NonNull
    private int daysValid;
    @OneToMany(mappedBy="subscriptionType")
    private List<User> users;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfSessions() {
        return numberOfSessions;
    }

    public void setNumberOfSessions(int numberOfSessions) {
        this.numberOfSessions = numberOfSessions;
    }

    public int getDaysValid() {
        return daysValid;
    }

    public void setDaysValid(int daysValid) {
        this.daysValid = daysValid;
    }
}
