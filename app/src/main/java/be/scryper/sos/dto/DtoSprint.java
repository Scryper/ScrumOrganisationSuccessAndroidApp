package be.scryper.sos.dto;

import android.os.Parcel;
import android.os.Parcelable;

public class DtoSprint implements Parcelable {
    private int id;
    private int idProject;
    private int sprintNumber;
    private String description;
    private String deadline;
    private String startDate;

    protected DtoSprint(Parcel in) {
        id = in.readInt();
        idProject = in.readInt();
        sprintNumber = in.readInt();
        description = in.readString();
        deadline = in.readString();
        startDate = in.readString();
    }

    public static final Creator<DtoSprint> CREATOR = new Creator<DtoSprint>() {
        @Override
        public DtoSprint createFromParcel(Parcel in) {
            return new DtoSprint(in);
        }

        @Override
        public DtoSprint[] newArray(int size) {
            return new DtoSprint[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProject() {
        return idProject;
    }

    public void setIdProject(int idProject) {
        this.idProject = idProject;
    }

    public int getSprintNumber() {
        return sprintNumber;
    }

    public void setSprintNumber(int sprintNumber) {
        this.sprintNumber = sprintNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return "DtoSprint{" +
                "id=" + id +
                ", idProject=" + idProject +
                ", sprintNumber=" + sprintNumber +
                ", description='" + description + '\'' +
                ", deadline=" + deadline +
                ", startDate=" + startDate +
                '}';
    }

    public DtoSprint(int id, int idProject, int sprintNumber, String description, String deadline, String startDate) {
        this.id = id;
        this.idProject = idProject;
        this.sprintNumber = sprintNumber;
        this.description = description;
        this.deadline = deadline;
        this.startDate = startDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(idProject);
        parcel.writeInt(sprintNumber);
        parcel.writeString(description);
        parcel.writeString(deadline);
        parcel.writeString(startDate);
    }
}
