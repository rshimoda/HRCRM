package com.eisgroup.hrcrm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class PerformanceAppraisalDetail extends BaseObject {

    @ManyToOne
    @JoinColumn
    private PerformanceAppraisal performanceAppraisal;

    @ManyToOne
    @JoinColumn
    private Question question;

    @ManyToOne
    @JoinColumn
    private User user;

    @Column
    private String answer;

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question questions) {
        this.question = questions;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public PerformanceAppraisal getPerformanceAppraisal() {
        return performanceAppraisal;
    }

    public void setPerformanceAppraisal(PerformanceAppraisal performanceAppraisal) {
        this.performanceAppraisal = performanceAppraisal;
    }
}
