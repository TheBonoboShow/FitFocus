package be.spacedandy.FitFocus.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    @NonNull
    private String username;
    @NonNull
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private boolean reminderSms;
    private boolean reminderMail;
    private boolean promotionsActive;
    private boolean isFemale;
    @ManyToOne
    @JoinColumn(name="roleid", insertable=false, updatable=false)
    private Role role;
    @ManyToMany
    private List<Session> reservedSessions;
    @ManyToOne
    @JoinColumn(name="subscriptiontypeid", insertable=false, updatable=false)
    private SubscriptionType subscriptionType;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean profileIsActive;
    private boolean profileIsSuspended;

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

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public boolean isPromotionsActive() {
        return promotionsActive;
    }

    public void setPromotionsActive(boolean promotionsActive) {
        this.promotionsActive = promotionsActive;
    }

    public boolean isFemale() {
        return isFemale;
    }

    public void setFemale(boolean female) {
        isFemale = female;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Session> getReservedSessions() {
        return reservedSessions;
    }

    public void setReservedSessions(List<Session> reservedSessions) {
        this.reservedSessions = reservedSessions;
    }

    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(SubscriptionType subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public boolean isProfileIsActive() {
        return profileIsActive;
    }

    public void setProfileIsActive(boolean profileIsActive) {
        this.profileIsActive = profileIsActive;
    }

    public boolean isProfileIsSuspended() {
        return profileIsSuspended;
    }

    public void setProfileIsSuspended(boolean profileIsSuspended) {
        this.profileIsSuspended = profileIsSuspended;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
