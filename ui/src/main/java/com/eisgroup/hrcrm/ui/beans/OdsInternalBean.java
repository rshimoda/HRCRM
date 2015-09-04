package com.eisgroup.hrcrm.ui.beans;

import com.eisgroup.hrcrm.model.ODSInternal;
import com.eisgroup.hrcrm.model.Status;
import com.eisgroup.hrcrm.service.TaskService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;

@ManagedBean
@ViewScoped
public class OdsInternalBean extends TaskBean implements Serializable {
    @ManagedProperty(value = "#{odsInternalService}")
    private TaskService taskService;

    /**
     * Initializes class fields from http-parameters
     */
    @Override
    @PostConstruct
    public void init() {
        super.setTaskService(taskService);
        super.setTaskType(ODSInternal.class.getSimpleName());
        super.init();
        setTaskType(ODSInternal.class.getName());

        if (getTask() == null) {
            setTask(new ODSInternal());
            getTask().setStatus(Status.OPEN);
        }
    }

    /**
     * Overrides root method to get specified TaskClass type
     *
     * @return ODSInternal
     */
    @Override
    public ODSInternal getTask() {
        return (ODSInternal) super.getTask();
    }

    /**
     * Overrides toot method to support injection of TaskClass specified service
     *
     * @param taskService
     */
    @Override
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

}

