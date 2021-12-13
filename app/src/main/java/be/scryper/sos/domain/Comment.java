package be.scryper.sos.domain;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalDateTime;

public class Comment implements Parcelable {
    private String texte;
    private LocalDateTime date;


    protected Comment(Parcel in) {
        texte = in.readString();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            date = (LocalDateTime) in.readSerializable();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(texte);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dest.writeSerializable(date);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };

    public Comment(String texte, LocalDateTime date) {
        this.texte = texte;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "texte='" + texte + '\'' +
                ", date=" + date +
                '}';
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
