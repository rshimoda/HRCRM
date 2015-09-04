package com.eisgroup.hrcrm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity(name = "COMMENTS")
public class Comment extends BaseObject {

    @ManyToOne
    @JoinColumn
    private Task task;

    @ManyToOne
    @JoinColumn
    private User user;

    @Column
    private Date tsdate;

    @Column
    private String taskcomments;

    public Comment() {

    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return tsdate;
    }

    public void setDate(Date timestamp) {
        this.tsdate = timestamp;
    }

    public String getComment() {
        return taskcomments;
    }

    public void setComment(String comment) {
        this.taskcomments = comment;
    }
}

