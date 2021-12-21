package be.scryper.sos.dto;

public class DtoInputMeeting {

    private int id;
    private int idSprint;
    private String schedule;
    private String description;
    private String meetingUrl;


    public DtoInputMeeting(int id, int idSprint, String schedule, String description, String meetingUrl) {
        this.id = id;
        this.idSprint = idSprint;
        this.schedule = schedule;
        this.description = description;
        this.meetingUrl = meetingUrl;
    }

    @Override
    public String toString() {
        return "DtoMeeting{" +
                "id=" + id +
                ", idSprint=" + idSprint +
                ", schedule=" + schedule +
                ", description='" + description + '\'' +
                ", meetingUrl='" + meetingUrl + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdSprint() {
        return idSprint;
    }

    public void setIdSprint(int idSprint) {
        this.idSprint = idSprint;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMeetingUrl() {
        return meetingUrl;
    }

    public void setMeetingUrl(String meetingUrl) {
        this.meetingUrl = meetingUrl;
    }
}
