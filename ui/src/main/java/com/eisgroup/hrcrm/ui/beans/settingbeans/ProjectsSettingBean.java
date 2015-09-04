package com.eisgroup.hrcrm.ui.beans.settingbeans;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;

@ManagedBean
@ViewScoped
public class ProjectsSettingBean extends SettingsBean implements Serializable {

    private boolean isProjectEditMode;

    private String projects;

    @PostConstruct
    public void init() {
        super.setEditMode(false);

        projects = taskPropertyService.getProjectsInString();


        setEditMode(false);
        setProjectEditMode(false);

    }

    public void save() {


        if (isProjectEditMode()) {
            taskPropertyService.updateProjects(projects);
        }
        init();
    }

    public void startEdit(String mode) {
        super.setEditMode(true);
        setProjectEditMode(true);
    }

    //Setters and getters section

    public boolean isProjectEditMode() {
        if (!super.isEditMode() && isProjectEditMode) {
            init();
        }
        return isProjectEditMode;
    }

    public void setProjectEditMode(boolean isProjectEditMode) {
        this.isProjectEditMode = isProjectEditMode;
    }

    public String getProjects() {
        return projects;
    }

    public void setProjects(String projects) {
        this.projects = projects;
    }
}
