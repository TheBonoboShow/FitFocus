package be.spacedandy.FitFocus.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "session", schema = "fitfocus")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToMany(mappedBy = "reservedSessions", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<User> participants;
    @ManyToOne
    @JoinColumn(name="userid", insertable=false, updatable=false)
    private User coach;
    private Integer userid;
    @Lob
    @Type(type = "text")
    private String information;
    @ManyToOne
    @JoinColumn(name="sportid", insertable=false, updatable=false)
    private Sport sport;
    private Integer sportid;
    private String date;
    private String startingHour;
    private String endHour;
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

    public String getEndHour() {
        return endHour;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
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

    @Override
    public String toString() {
        return "Session{" +
                "id=" + id +
                ", coach=" + coach +
                ", userid=" + userid +
                ", information='" + information + '\'' +
                ", sport=" + sport +
                ", sportid=" + sportid +
                ", date='" + date + '\'' +
                ", startingHour='" + startingHour + '\'' +
                ", endHour='" + endHour + '\'' +
                ", maxParticipants=" + maxParticipants +
                ", onlyFemales=" + onlyFemales +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return id == session.id && maxParticipants == session.maxParticipants && onlyFemales == session.onlyFemales && coach.equals(session.coach) && userid.equals(session.userid) && information.equals(session.information) && sport.equals(session.sport) && sportid.equals(session.sportid) && date.equals(session.date) && startingHour.equals(session.startingHour) && endHour.equals(session.endHour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, coach, userid, information, sport, sportid, date, startingHour, endHour, maxParticipants, onlyFemales);
    }
}
