package be.scryper.sos.dto;

import android.os.Parcel;
import android.os.Parcelable;

public class DtoAuthenticateResult implements Parcelable {
    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private int role;
    private String profilePicture;
    private String token;

    protected DtoAuthenticateResult(Parcel in) {
        firstname = in.readString();
        lastname = in.readString();
        email = in.readString();
        role = in.readInt();
        id = in.readInt();
        profilePicture = in.readString();
        token = in.readString();
    }

    public static final Creator<DtoAuthenticateResult> CREATOR = new Creator<DtoAuthenticateResult>() {
        @Override
        public DtoAuthenticateResult createFromParcel(Parcel in) {
            return new DtoAuthenticateResult(in);
        }

        @Override
        public DtoAuthenticateResult[] newArray(int size) {
            return new DtoAuthenticateResult[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(firstname);
        parcel.writeString(lastname);
        parcel.writeString(email);
        parcel.writeInt(role);
        parcel.writeInt(id);
        parcel.writeString(profilePicture);
        parcel.writeString(token);
    }

    @Override
    public String toString() {
        return "DtoAuthenticateResult{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", id=" + id +
                ", profile picture='" + profilePicture + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}
