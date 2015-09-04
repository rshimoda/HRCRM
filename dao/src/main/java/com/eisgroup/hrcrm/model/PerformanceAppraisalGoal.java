package com.eisgroup.hrcrm.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class PerformanceAppraisalGoal extends BaseObject {

    @ManyToOne
    @JoinColumn
    private PerformanceAppraisal performanceAppraisal;

    @ManyToOne
    @JoinColumn
    private User user;

    private String goal;

    private String goalAnswer;

    public PerformanceAppraisal getPerformanceAppraisal() {
        return performanceAppraisal;
    }

    public void setPerformanceAppraisal(PerformanceAppraisal performanceAppraisal) {
        this.performanceAppraisal = performanceAppraisal;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getGoalAnswer() {
        return goalAnswer;
    }

    public void setGoalAnswer(String goalAnswer) {
        this.goalAnswer = goalAnswer;
    }
}
