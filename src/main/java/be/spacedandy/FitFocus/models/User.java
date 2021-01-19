package be.spacedandy.FitFocus.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(min = 3, max = 20, message = "Username must be at least 3 characters")
    private String username;

    @Size(min = 6, message = "Your password must be at least 6 characters")
    private String password;

    private String firstname;

    private String lastname;

    @NotBlank(message = "Email can not be empty")
    @Email(message = "Please provide a valid email address")
    private String email;

    private boolean reminderMail = false;

    private boolean female = false;

    private boolean promotionsActive = false;

    @ManyToOne
    @JoinColumn(name = "roleid", insertable = false, updatable = false)
    private Role role;

    private Integer roleid = 3;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "session_participants_join",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "session_id"))
    @JsonIgnore
    private List<Session> reservedSessions;

    @ManyToOne
    @JoinColumn(name = "subscriptiontypeid", insertable = false, updatable = false)
    private SubscriptionType subscriptionType;

    private Integer subscriptiontypeid;

    private String startDate = LocalDate.now().toString();

    private String endDate = LocalDate.now().plusMonths(3).toString();

    private boolean profileIsActive = false;

    private boolean profileIsSuspended = false;

    private int remainingSessions = 1;

    private String verificationToken;

    private String passwordResetToken;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean isReminderMail() {
        return reminderMail;
    }

    public void setReminderMail(boolean reminderMail) {
        this.reminderMail = reminderMail;
    }

    public boolean isFemale() {
        return female;
    }

    public void setFemale(boolean female) {
        this.female = female;
    }

    public boolean isPromotionsActive() {
        return promotionsActive;
    }

    public void setPromotionsActive(boolean promotionsActive) {
        this.promotionsActive = promotionsActive;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
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

    public Integer getSubscriptiontypeid() {
        return subscriptiontypeid;
    }

    public void setSubscriptiontypeid(Integer subscriptiontypeid) {
        this.subscriptiontypeid = subscriptiontypeid;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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

    public int getRemainingSessions() {
        return remainingSessions;
    }

    public void setRemainingSessions(int remainingSessions) {
        this.remainingSessions = remainingSessions;
    }

    public String getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(String verificationCode) {
        this.verificationToken = verificationCode;
    }

    public String getPasswordResetToken() {
        return passwordResetToken;
    }

    public void setPasswordResetToken(String passwordResetToken) {
        this.passwordResetToken = passwordResetToken;
    }

    @Override
    public String toString() {
        return firstname + " " + lastname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && reminderMail == user.reminderMail && female == user.female && promotionsActive == user.promotionsActive && profileIsActive == user.profileIsActive && profileIsSuspended == user.profileIsSuspended && remainingSessions == user.remainingSessions && username.equals(user.username) && password.equals(user.password) && Objects.equals(firstname, user.firstname) && Objects.equals(lastname, user.lastname) && email.equals(user.email) && Objects.equals(role, user.role) && Objects.equals(roleid, user.roleid) && Objects.equals(subscriptionType, user.subscriptionType) && Objects.equals(subscriptiontypeid, user.subscriptiontypeid) && Objects.equals(startDate, user.startDate) && Objects.equals(endDate, user.endDate) && Objects.equals(verificationToken, user.verificationToken) && Objects.equals(passwordResetToken, user.passwordResetToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, firstname, lastname, email, reminderMail, female, promotionsActive, role, roleid, subscriptionType, subscriptiontypeid, startDate, endDate, profileIsActive, profileIsSuspended, remainingSessions, verificationToken, passwordResetToken);
    }
}
