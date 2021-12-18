package be.scryper.sos.dto;

import java.time.LocalDateTime;

public class DtoProject {
    private int id;
    private int idProductOwner;
    private int IdScrumMaster;
    private String name;
    private LocalDateTime Deadline;
    private String description;
    private String repositoryUrl;
    private int status;

    public DtoProject(int id, int idProductOwner, int idScrumMaster, String name, LocalDateTime deadline, String description, String repositoryUrl, int status) {
        this.id = id;
        this.idProductOwner = idProductOwner;
        IdScrumMaster = idScrumMaster;
        this.name = name;
        Deadline = deadline;
        this.description = description;
        this.repositoryUrl = repositoryUrl;
        this.status = status;
    }

    @Override
    public String toString() {
        return "DtoProject{" +
                "id=" + id +
                ", idProductOwner=" + idProductOwner +
                ", IdScrumMaster=" + IdScrumMaster +
                ", name='" + name + '\'' +
                ", Deadline=" + Deadline +
                ", description='" + description + '\'' +
                ", repositoryUrl='" + repositoryUrl + '\'' +
                ", status=" + status +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProductOwner() {
        return idProductOwner;
    }

    public void setIdProductOwner(int idProductOwner) {
        this.idProductOwner = idProductOwner;
    }

    public int getIdScrumMaster() {
        return IdScrumMaster;
    }

    public void setIdScrumMaster(int idScrumMaster) {
        IdScrumMaster = idScrumMaster;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDeadline() {
        return Deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        Deadline = deadline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRepositoryUrl() {
        return repositoryUrl;
    }

    public void setRepositoryUrl(String repositoryUrl) {
        this.repositoryUrl = repositoryUrl;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
