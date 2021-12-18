package be.scryper.sos.dto;

import android.os.Parcel;
import android.os.Parcelable;

public class DtoComment implements Parcelable {
    private int id;
    private int idUserStory;
    private int idUser;
    private String postedAt;
    private String content;


    protected DtoComment(Parcel in) {
        id = in.readInt();
        idUserStory = in.readInt();
        idUser = in.readInt();
        postedAt = in.readString();
        content = in.readString();
    }

    public static final Creator<DtoComment> CREATOR = new Creator<DtoComment>() {
        @Override
        public DtoComment createFromParcel(Parcel in) {
            return new DtoComment(in);
        }

        @Override
        public DtoComment[] newArray(int size) {
            return new DtoComment[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(idUserStory);
        parcel.writeInt(idUser);
        parcel.writeString(postedAt);
        parcel.writeString(content);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUserStory() {
        return idUserStory;
    }

    public void setIdUserStory(int idUserStory) {
        this.idUserStory = idUserStory;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(String postedAt) {
        this.postedAt = postedAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "DtoComment{" +
                "id=" + id +
                ", idUserStory=" + idUserStory +
                ", idUser=" + idUser +
                ", postedAt='" + postedAt + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public DtoComment(int id, int idUserStory, int idUser, String postedAt, String content) {
        this.id = id;
        this.idUserStory = idUserStory;
        this.idUser = idUser;
        this.postedAt = postedAt;
        this.content = content;
    }
}