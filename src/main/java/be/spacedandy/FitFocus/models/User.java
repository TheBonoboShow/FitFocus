package be.spacedandy.FitFocus.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    @NonNull
    private String username;
    @NonNull
    private String password;
    @NonNull
    private String firstname;
    @NonNull
    private String lastname;
    private boolean reminderSms;
    private boolean reminderMail;
    private boolean isFemale;

    @ManyToMany
    private List<Session> plannedSessions;

    @ManyToOne
    @JoinColumn(name="subscriptiontypeid", insertable=false, updatable=false)
    private SubscriptionType subscriptionType;

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstname;
    }

    public void setFirstName(String firstName) {
        this.firstname = firstName;
    }

    public String getLastName() {
        return lastname;
    }

    public void setLastName(String lastName) {
        this.lastname = lastName;
    }

    public boolean isReminderSms() {
        return reminderSms;
    }

    public void setReminderSms(boolean reminderSms) {
        this.reminderSms = reminderSms;
    }

    public boolean isReminderMail() {
        return reminderMail;
    }

    public void setReminderMail(boolean reminderMail) {
        this.reminderMail = reminderMail;
    }

    public boolean isFemale() {
        return isFemale;
    }

    public void setFemale(boolean female) {
        isFemale = female;
    }

    public List<Session> getPlannedSessions() {
        return plannedSessions;
    }

    public void setPlannedSessions(List<Session> plannedSessions) {
        this.plannedSessions = plannedSessions;
    }

    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(SubscriptionType subscriptionType) {
        this.subscriptionType = subscriptionType;
    }
}
