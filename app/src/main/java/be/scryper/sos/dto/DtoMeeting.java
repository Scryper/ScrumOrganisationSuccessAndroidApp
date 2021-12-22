package be.scryper.sos.dto;

import java.time.LocalDateTime;
import java.util.Date;

public class DtoMeeting {
    private int id;
    private int idSprint;
    private Date schedule;
    private String description;
    private String meetingUrl;


    public DtoMeeting(int id, int idSprint, Date schedule, String description, String meetingUrl) {
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

    public Date getSchedule() {
        return schedule;
    }

    public void setSchedule(Date schedule) {
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

    public static DtoMeeting combine(DtoInputMeeting dtoInputMeeting, Date dateTime){
        return new DtoMeeting(dtoInputMeeting.getId(),
                dtoInputMeeting.getIdSprint(),
                dateTime,
                dtoInputMeeting.getDescription(),
                dtoInputMeeting.getMeetingUrl());
    }
}
