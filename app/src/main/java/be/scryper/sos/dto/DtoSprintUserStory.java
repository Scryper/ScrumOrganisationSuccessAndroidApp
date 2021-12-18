package be.scryper.sos.dto;

public class DtoSprintUserStory {
    private int idSprint;
    private int idUserStory;

    public int getIdSprint() {
        return idSprint;
    }

    public void setIdSprint(int idSprint) {
        this.idSprint = idSprint;
    }

    public int getIdUserStory() {
        return idUserStory;
    }

    public void setIdUserStory(int idUserStory) {
        this.idUserStory = idUserStory;
    }

    @Override
    public String toString() {
        return "DtoSprintUserStory{" +
                "idSprint=" + idSprint +
                ", idUserStory=" + idUserStory +
                '}';
    }

    public DtoSprintUserStory(int idSprint, int idUserStory) {
        this.idSprint = idSprint;
        this.idUserStory = idUserStory;
    }
}
