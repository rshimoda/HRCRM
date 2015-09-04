package com.eisgroup.hrcrm.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class PerformanceAppraisal extends Task {
    @ManyToOne
    @JoinColumn
    private Project project;

    @ManyToOne()
    @JoinColumn
    private Position position;

    @ManyToOne
    @JoinColumn
    private CandidateLevel candidateLevel;

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
