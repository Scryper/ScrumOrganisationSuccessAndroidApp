package be.scryper.sos.dto;

public class DtoDeveloperProject {
    private int idDeveloper;
    private int idProject;
    private boolean isAppliance;

    public int getIdDeveloper() {
        return idDeveloper;
    }

    public void setIdDeveloper(int idDeveloper) {
        this.idDeveloper = idDeveloper;
    }

    public int getIdProject() {
        return idProject;
    }

    public void setIdProject(int idProject) {
        this.idProject = idProject;
    }

    public boolean isAppliance() {
        return isAppliance;
    }

    public void setIsAppliance(boolean isAppliance) {
        this.isAppliance = isAppliance;
    }

    @Override
    public String toString() {
        return "DtoDeveloperProject{" +
                "idDeveloper=" + idDeveloper +
                ", idProject=" + idProject +
                ", isAppliance=" + isAppliance +
                '}';
    }

    public DtoDeveloperProject(int idDeveloper, int idProject, boolean isAppliance) {
        this.idDeveloper = idDeveloper;
        this.idProject = idProject;
        this.isAppliance = isAppliance;
    }
}
