package com.eisgroup.hrcrm.model;

import javax.persistence.*;

@Entity
public class Candidate extends Task {

    @ManyToOne
    @JoinColumn
    private Project project;

    @ManyToOne
    @JoinColumn
    private Position position;

    @ManyToOne
    @JoinColumn
    private CandidateLevel candidateLevel;

    @Enumerated(EnumType.STRING)
    private Source source;

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

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

}
