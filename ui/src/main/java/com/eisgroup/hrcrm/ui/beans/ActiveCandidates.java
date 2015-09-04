package com.eisgroup.hrcrm.ui.beans;

import com.eisgroup.hrcrm.model.Candidate;
import com.eisgroup.hrcrm.service.RecruitmentService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@ViewScoped
public class ActiveCandidates implements Serializable {

    private List<Candidate> activeCandidate;

    @ManagedProperty(value = "#{recruitmentService}")
    private RecruitmentService recruitmentService;

    @PostConstruct
    void init() {
        setActiveCandidate(recruitmentService.getAllActiveCandidate());
    }

    public List<Candidate> getActiveCandidate() {
        return activeCandidate;
    }

    public void setActiveCandidate(List<Candidate> activeCandidate) {
        this.activeCandidate = activeCandidate;
    }

    public RecruitmentService getRecruitmentService() {
        return recruitmentService;
    }

    public void setRecruitmentService(RecruitmentService recruitmentService) {
        this.recruitmentService = recruitmentService;
    }
}
