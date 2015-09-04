package com.eisgroup.hrcrm.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class Recruitment extends Task {
    @ManyToOne
    @JoinColumn
    private Project project;

    @ManyToOne()
    @JoinColumn
    private Position position;

    @ManyToOne
    @JoinColumn
    private CandidateLevel candidateLevel;

    public Recruitment() {

    }

    public Recruitment(long id, Status status, Priority priority, Complexity complexity, Resolution resolution, String summary,
                       String description, Date creationDate, Date dueDate, Date updateDate, Date closedDate, User creator,
                       User assignee, Project project, Position position, CandidateLevel candidateLevel) {
        super(status, priority, complexity, resolution, summary, description, creationDate, dueDate, updateDate, closedDate, creator, assignee);
        this.project = project;
        this.position = position;
        this.candidateLevel = candidateLevel;
        setId(id);
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public CandidateLevel getCandidateLevel() {
        return candidateLevel;
    }

    public void setCandidateLevel(CandidateLevel candidateLevel) {
        this.candidateLevel = candidateLevel;
    }
}
