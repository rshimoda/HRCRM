package com.eisgroup.hrcrm.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class PerformanceAppraisalUser extends BaseObject {
    @ManyToOne
    @JoinColumn
    private PerformanceAppraisal performanceAppraisal;

    @ManyToOne
    @JoinColumn
    private User appraisalUser;

    private boolean isReviewer;


    public User getAppraisalUser() {
        return appraisalUser;
    }

    public void setAppraisalUser(User appraisalUser) {
        this.appraisalUser = appraisalUser;
    }


    public boolean isReviewer() {
        return isReviewer;
    }

    public void setReviewer(boolean isReviewer) {
        this.isReviewer = isReviewer;
    }


    public PerformanceAppraisal getPerformanceAppraisal() {
        return performanceAppraisal;
    }

    public void setPerformanceAppraisal(PerformanceAppraisal performanceAppraisal) {
        this.performanceAppraisal = performanceAppraisal;
    }
}








