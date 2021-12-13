package be.scryper.sos.domain;

import android.os.Parcel;
import android.os.Parcelable;

public class UserStory implements Parcelable {
    private int id;
    private String name;
    private String description;
    private String isDone;

    protected UserStory(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        isDone = in.readString();
    }

    public UserStory(int id, String name, String description, String isDone) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isDone = isDone;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(isDone);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserStory> CREATOR = new Creator<UserStory>() {
        @Override
        public UserStory createFromParcel(Parcel in) {
            return new UserStory(in);
        }

        @Override
        public UserStory[] newArray(int size) {
            return new UserStory[size];
        }
    };

    @Override
    public String toString() {
        return "UserStory{" +
                "id=" + id +
                ", nom='" + name + '\'' +
                ", description='" + description + '\'' +
                ", isDone=" + isDone +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDone() {
        return isDone;
    }

    public void setDone(String done) {
        this.isDone = done;
    }
}
