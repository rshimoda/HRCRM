package com.eisgroup.hrcrm.ui.beans;

import com.eisgroup.hrcrm.model.Candidate;
import com.eisgroup.hrcrm.model.Recruitment;
import com.eisgroup.hrcrm.model.Status;
import com.eisgroup.hrcrm.service.CandidateService;
import com.eisgroup.hrcrm.service.TaskService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ManagedBean
@ViewScoped
public class CandidateBean extends TaskBean implements Serializable {

    @ManagedProperty(value = "#{candidateService}")
    private TaskService taskService;

    @ManagedProperty(value = "#{candidateService}")
    private CandidateService candidateService;

    private List<Recruitment> linkedRecruitments;
    private boolean isShownLinkedRecruitments;

    private List<Recruitment> activeRecruitment;
    private List<Recruitment> selectedRecruitments;
    private List<String> usedRecruitmentPositions;
    private List<String> usedRecruitmentLevels;
    //private String isResolved;

    @PostConstruct
    public void init() {
        super.setTaskService(taskService);
        super.setTaskType(Candidate.class.getSimpleName());
        super.init();

        setActiveRecruitment(candidateService.getAllActiveRecruiment());
        setUsedRecruitmentLevels(candidateService.getAllRecruitmentLevels());
        setUsedRecruitmentPositions(candidateService.getAllRecruitmentPositions());

        if (getTask() == null) {
            setTask(new Candidate());
            getTask().setStatus(Status.OPEN);
        } else {
            //setIsResolved(updateLinkButton());
            linkedRecruitments = candidateService.getAllRecruitmentsByCandidateId(getTask().getId());
            selectedRecruitments = candidateService.getAllRecruitmentsByCandidateId(getTask().getId());
            if (!linkedRecruitments.isEmpty()) {
                setShownLinkedRecruitments(true);
            }
        }
    }

    public void onLinkClicked() {
        candidateService.linkActiveRecruitments(getTask().getId(), selectedRecruitments);
        init();
        getTask().setUpdateDate(new Date());
        if (getLinkedRecruitments().isEmpty()) {
            setShownLinkedRecruitments(false);
        } else {
            setShownLinkedRecruitments(true);
        }
    }

    @Override
    public void reopen() {
        super.reopen();
        init();
    }

    @Override
    public void create() {
        //Complexity and Priority cannot be NULL so we have to add some reference to
        //Existing Complexity and Priority
        getTask().setComplexity(getTaskPropertyService().getComplexityById((long) 1));
        getTask().setPriority(getPriorities()[0]);
        super.create();
    }

    /**
     * Overrides root method to get specified TaskClass type
     *
     * @return Recruitment
     */
    @Override
    public Candidate getTask() {
        return (Candidate) super.getTask();
    }

    /**
     * Overrides toot method to support injection of TaskClass specified service
     *
     * @param taskService
     */
    @Override
    public void setTaskService(TaskService taskService) {
        super.setTaskService(taskService);
        this.taskService = taskService;
    }

    public void setCandidateService(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    public List<Recruitment> getLinkedRecruitments() {
        return linkedRecruitments;
    }

    public void setLinkedRecruitments(List<Recruitment> linkedRecruitments) {
        this.linkedRecruitments = linkedRecruitments;
    }

    public boolean isShownLinkedRecruitments() {
        return isShownLinkedRecruitments;
    }

    public void setShownLinkedRecruitments(boolean isShownLinkedRecruitments) {
        this.isShownLinkedRecruitments = isShownLinkedRecruitments;
    }

    public List<Recruitment> getActiveRecruitment() {
        return activeRecruitment;
    }

    public void setActiveRecruitment(List<Recruitment> activeRecruitment) {
        this.activeRecruitment = activeRecruitment;
    }

    public List<Recruitment> getSelectedRecruitments() {
        return selectedRecruitments;
    }

    public void setSelectedRecruitments(List<Recruitment> selectedRecruitments) {
        this.selectedRecruitments = selectedRecruitments;
    }

    public List<String> getUsedRecruitmentPositions() {
        return usedRecruitmentPositions;
    }

    public void setUsedRecruitmentPositions(List<String> usedRecruitmentPositions) {
        this.usedRecruitmentPositions = usedRecruitmentPositions;
    }

    public List<String> getUsedRecruitmentLevels() {
        return usedRecruitmentLevels;
    }

    public void setUsedRecruitmentLevels(List<String> usedRecruitmentLevels) {
        this.usedRecruitmentLevels = usedRecruitmentLevels;
    }

//    public String getIsResolved() {
//        return isResolved;
//    }

//    public void setIsResolved(String isResolved) {
//        this.isResolved = isResolved;
//    }
}
