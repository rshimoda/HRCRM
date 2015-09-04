package com.eisgroup.hrcrm.ui.beans;

import com.eisgroup.hrcrm.model.Candidate;
import com.eisgroup.hrcrm.model.Recruitment;
import com.eisgroup.hrcrm.model.Status;
import com.eisgroup.hrcrm.service.RecruitmentService;
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
public class RecruitmentBean extends TaskBean implements Serializable {

    @ManagedProperty(value = "#{recruitmentService}")
    private TaskService taskService;

    @ManagedProperty(value = "#{recruitmentService}")
    private RecruitmentService recruitmentService;

    private List<Candidate> linkedCandidates;
    private boolean isShownLinkedCandidates;

    private List<Candidate> activeCandidate;
    private List<Candidate> selectedCandidates;
    private List<String> usedCandidatePositions;
    private List<String> usedCandidateLevels;
    //private String isResolved;


    /**
     * Initializes class fields from http-parameters
     */
    @PostConstruct
    public void init() {
        super.setTaskService(taskService);
        super.setTaskType(Recruitment.class.getSimpleName());
        super.init();

        setActiveCandidate(recruitmentService.getAllActiveCandidate());
        setUsedCandidatePositions(recruitmentService.getAllUsedCandidatePositions());
        setUsedCandidateLevels(recruitmentService.getAllUsedCandidateLevels());

        if (getTask() == null) {
            setTask(new Recruitment());
            getTask().setStatus(Status.OPEN);
        } else {
            //setIsResolved(updateLinkButton());
            linkedCandidates = recruitmentService.getAllCandidatesByRecruitmentId(getTask().getId());
            selectedCandidates = recruitmentService.getAllCandidatesByRecruitmentId(getTask().getId());
            if (!linkedCandidates.isEmpty()) {
                setShownLinkedCandidates(true);
            }
        }
    }

    public void onLinkClicked() {
        recruitmentService.linkActiveCandidates(getTask().getId(), selectedCandidates);
        init();
        getTask().setUpdateDate(new Date());
        if (getLinkedCandidates().isEmpty()) {
            setShownLinkedCandidates(false);
        } else {
            setShownLinkedCandidates(true);
        }
        redirectToTask();
    }

    @Override
    public void reopen() {
        super.reopen();
        init();
    }

    public void updateCandidate(Candidate candidate) {
        candidate.setUpdateDate(new Date());
        taskService.update(candidate);
        getTask().setUpdateDate(new Date());
        taskService.update(getTask());
    }

    /**
     * Overrides root method to get specified TaskClass type
     *
     * @return Recruitment
     */
    @Override
    public Recruitment getTask() {
        return (Recruitment) super.getTask();
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


    public void setRecruitmentService(RecruitmentService recruitmentService) {
        this.recruitmentService = recruitmentService;
    }

    public List<Candidate> getLinkedCandidates() {
        return linkedCandidates;
    }

    public void setLinkedCandidates(List<Candidate> linkedCandidates) {
        this.linkedCandidates = linkedCandidates;
    }

    public boolean isShownLinkedCandidates() {
        return isShownLinkedCandidates;
    }

    public void setShownLinkedCandidates(boolean isShownLinkedCandidates) {
        this.isShownLinkedCandidates = isShownLinkedCandidates;
    }

    public List<Candidate> getActiveCandidate() {
        return activeCandidate;
    }

    public void setActiveCandidate(List<Candidate> activeCandidate) {
        this.activeCandidate = activeCandidate;
    }

    public List<Candidate> getSelectedCandidates() {
        return selectedCandidates;
    }

    public void setSelectedCandidates(List<Candidate> selectedCandidates) {
        this.selectedCandidates = selectedCandidates;
    }

    public List<String> getUsedCandidatePositions() {
        return usedCandidatePositions;
    }

    public void setUsedCandidatePositions(List<String> usedCandidatePositions) {
        this.usedCandidatePositions = usedCandidatePositions;
    }

    public List<String> getUsedCandidateLevels() {
        return usedCandidateLevels;
    }

    public void setUsedCandidateLevels(List<String> usedCandidateLevels) {
        this.usedCandidateLevels = usedCandidateLevels;
    }

//    public String getIsResolved() {
//        return isResolved;
//    }

//    public void setIsResolved(String isResolved) {
//        this.isResolved = isResolved;
//    }
}

