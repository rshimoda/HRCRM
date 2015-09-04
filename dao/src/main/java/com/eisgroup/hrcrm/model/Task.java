package com.eisgroup.hrcrm.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TASK_TYPE")
public abstract class Task extends BaseObject {

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @ManyToOne(cascade = {})
    @JoinColumn
    private Complexity complexity;

    @ManyToOne(cascade = {})
    @JoinColumn
    private Resolution resolution;

    @Column
    private String summary;

    @Column
    private String description;

    @Column
    @Temporal(TemporalType.DATE)
    private Date creationDate;

    @Column
    @Temporal(TemporalType.DATE)
    private Date dueDate;

    @Column
    @Temporal(TemporalType.DATE)
    private Date updateDate;

    @Column
    @Temporal(TemporalType.DATE)
    private Date closedDate;

    @ManyToOne
    @JoinColumn
    private User creator;

    @ManyToOne
    @JoinColumn
    private User assignee;

    public Task() {

    }

    public Task(Status status, Priority priority, Complexity complexity, Resolution resolution, String summary, String description, Date creationDate, Date dueDate, Date updateDate, Date closedDate, User creator, User assignee) {
        this.status = status;
        this.priority = priority;
        this.complexity = complexity;
        this.resolution = resolution;
        this.summary = summary;
        this.description = description;
        this.creationDate = creationDate;
        this.dueDate = dueDate;
        this.updateDate = updateDate;
        this.closedDate = closedDate;
        this.creator = creator;
        this.assignee = assignee;
    }

    //--------------Setters and Getters section--------------------

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Complexity getComplexity() {
        return complexity;
    }

    public void setComplexity(Complexity complexity) {
        this.complexity = complexity;
    }

    public Resolution getResolution() {
        return resolution;
    }

    public void setResolution(Resolution resolution) {
        this.resolution = resolution;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(Date closedDate) {
        this.closedDate = closedDate;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }
}
