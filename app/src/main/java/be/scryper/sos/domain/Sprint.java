package be.scryper.sos.domain;

import android.os.Parcel;
import android.os.Parcelable;

public class Sprint implements Parcelable {
    private int id;
    private String description;

    protected Sprint(Parcel in) {
        id = in.readInt();
        description = in.readString();
    }

    public static final Creator<Sprint> CREATOR = new Creator<Sprint>() {
        @Override
        public Sprint createFromParcel(Parcel in) {
            return new Sprint(in);
        }

        @Override
        public Sprint[] newArray(int size) {
            return new Sprint[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(description);
    }

    public Sprint(int id, String description) {
        this.id = id;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Sprint{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
