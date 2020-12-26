package be.spacedandy.FitFocus.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToMany
    private List<User> participants;
    @ManyToOne
    @JoinColumn(name="userid", insertable=false, updatable=false)
    private User coach;
    private Integer userid;
    @Lob
    private String information;
    @ManyToOne
    @JoinColumn(name="sportid", insertable=false, updatable=false)
    private Sport sport;
    private Integer sportid;
    private String date;
    private String startingHour;
    private int duration;
    private int maxParticipants;
    private boolean onlyFemales;

    public int getId() {
        return id;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public User getCoach() {
        return coach;
    }

    public void setCoach(User coach) {
        this.coach = coach;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartingHour() {
        return startingHour;
    }

    public void setStartingHour(String startingHour) {
        this.startingHour = startingHour;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public boolean isOnlyFemales() {
        return onlyFemales;
    }

    public void setOnlyFemales(boolean onlyFemales) {
        this.onlyFemales = onlyFemales;
    }
}
