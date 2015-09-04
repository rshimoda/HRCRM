package com.eisgroup.hrcrm.ui.beans;

import com.eisgroup.hrcrm.model.Task;
import com.eisgroup.hrcrm.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;

@ManagedBean
@ViewScoped
public class ViewTaskBean implements Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(ViewTaskBean.class);

    @ManagedProperty(value = "#{taskService}")
    private TaskService taskService;

    private boolean isShowOdsInternal = false;
    private boolean isShowRecruitment = false;
    private boolean isShowMarketing = false;
    private boolean isShowPA = false;
    private boolean isShowCandidates = false;


    /**
     * Used for inject service
     *
     * @param taskService Spring service
     */
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Init method to create all needed environment
     */
    @PostConstruct
    public void init() {
        String taskId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("taskId");
        if (taskId != null && !taskId.isEmpty()) {
            try {
                Task task = taskService.find(Long.valueOf(taskId));
                if (task == null) {
                    return;
                }

                String taskType = task.getClass().getSimpleName();
                if ("ODSInternal".equals(taskType)) {
                    setShowOdsInternal(true);
                    return;
                }
                if ("Recruitment".equals(taskType)) {
                    setShowRecruitment(true);
                    return;
                }
                if ("Marketing".equals(taskType)) {
                    setShowMarketing(true);
                    return;
                }

                if ("Candidate".equals(taskType)) {
                    setShowCandidates(true);
                    return;
                }
                if ("PerformanceAppraisal".equals(taskType)) {
                    setShowPA(true);
                    return;
                }
            } catch (Exception e) {
                LOG.info("init exception: ", e);
            }
        }
    }

    public boolean isShowOdsInternal() {
        return isShowOdsInternal;
    }

    public void setShowOdsInternal(boolean isShowOdsInternal) {
        this.isShowOdsInternal = isShowOdsInternal;
    }

    public boolean isShowRecruitment() {
        return isShowRecruitment;
    }

    public void setShowRecruitment(boolean isShowRecruitment) {
        this.isShowRecruitment = isShowRecruitment;
    }

    public boolean isShowMarketing() {
        return isShowMarketing;
    }

    public void setShowMarketing(boolean isShowMarketing) {
        this.isShowMarketing = isShowMarketing;
    }

    public boolean isShowPA() {
        return isShowPA;
    }

    public void setShowPA(boolean isShowPA) {
        this.isShowPA = isShowPA;
    }

    public boolean isShowCandidates() {
        return isShowCandidates;
    }

    public void setShowCandidates(boolean isShowCandidates) {
        this.isShowCandidates = isShowCandidates;
    }
}