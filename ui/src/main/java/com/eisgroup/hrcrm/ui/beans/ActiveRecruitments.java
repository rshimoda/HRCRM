package com.eisgroup.hrcrm.ui.beans;

import com.eisgroup.hrcrm.model.Recruitment;
import com.eisgroup.hrcrm.service.CandidateService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@ViewScoped
public class ActiveRecruitments implements Serializable {

    private List<Recruitment> activeRecruitment;

    @ManagedProperty(value = "#{candidateService}")
    private CandidateService candidateService;

    @PostConstruct()
    void init() {
        setActiveRecruitment(candidateService.getAllActiveRecruiment());
    }

    public List<Recruitment> getActiveRecruitment() {
        return activeRecruitment;
    }

    public void setActiveRecruitment(List<Recruitment> activeRecruitments) {
        this.activeRecruitment = activeRecruitments;
    }

    public CandidateService getCandidateService() {
        return candidateService;
    }

    public void setCandidateService(CandidateService candidateService) {
        this.candidateService = candidateService;
    }
}
