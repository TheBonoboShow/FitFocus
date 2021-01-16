package be.spacedandy.FitFocus.models;

import java.util.List;

public class Event {
    private String title;
    private String start;
    private String end;
    private int participants;
    private String coach;
    private String information;
    private int maxParticipants;
    private boolean onlyFemales;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public int getParticipants() {
        return participants;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }

    public String getCoach() {
        return coach;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
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
