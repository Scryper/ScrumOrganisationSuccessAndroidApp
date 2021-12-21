package be.scryper.sos.dto;

import java.time.LocalDateTime;

public class DtoMeeting {
    private int id;
    private int idSprint;
    private LocalDateTime schedule;
    private String description;
    private String meetingUrl;


    public DtoMeeting(int id, int idSprint, LocalDateTime schedule, String description, String meetingUrl) {
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

    public LocalDateTime getSchedule() {
        return schedule;
    }

    public void setSchedule(LocalDateTime schedule) {
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

    public static DtoMeeting combine(DtoInputMeeting dtoInputMeeting, LocalDateTime localDateTime){
        return new DtoMeeting(dtoInputMeeting.getId(),
                dtoInputMeeting.getIdSprint(),
                localDateTime,
                dtoInputMeeting.getDescription(),
                dtoInputMeeting.getMeetingUrl());
    }
}
