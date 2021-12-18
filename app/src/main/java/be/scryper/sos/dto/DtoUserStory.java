package be.scryper.sos.dto;

import android.os.Parcel;
import android.os.Parcelable;

public class DtoUserStory implements Parcelable {
    private int id;
    private int idProject;
    private String name;
    private String description;
    private int priority;

    protected DtoUserStory(Parcel in) {
        id = in.readInt();
        idProject = in.readInt();
        name = in.readString();
        description = in.readString();
        priority = in.readInt();
    }

    public static final Creator<DtoUserStory> CREATOR = new Creator<DtoUserStory>() {
        @Override
        public DtoUserStory createFromParcel(Parcel in) {
            return new DtoUserStory(in);
        }

        @Override
        public DtoUserStory[] newArray(int size) {
            return new DtoUserStory[size];
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "DtoUserStory{" +
                "id=" + id +
                ", idProject=" + idProject +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", priority=" + priority +
                '}';
    }

    public DtoUserStory(int id, int idProject, String name, String description, int priority) {
        this.id = id;
        this.idProject = idProject;
        this.name = name;
        this.description = description;
        this.priority = priority;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(idProject);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeInt(priority);
    }
}