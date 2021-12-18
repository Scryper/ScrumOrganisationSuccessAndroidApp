package be.scryper.sos.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DtoCreateComment {
    private int idUserStory;
    private int idUser;
    private String postedAt;
    private String content;

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
        return "DtoCreateComment{" +
                "idUserStory=" + idUserStory +
                ", idUser=" + idUser +
                ", postedAt=" + postedAt +
                ", content='" + content + '\'' +
                '}';
    }

    public DtoCreateComment(int idUserStory, int idUser, String postedAt, String content) {
        this.idUserStory = idUserStory;
        this.idUser = idUser;
        this.postedAt = postedAt;
        this.content = content;
    }
}
