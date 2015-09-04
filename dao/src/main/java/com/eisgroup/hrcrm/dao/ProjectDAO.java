package com.eisgroup.hrcrm.dao;

import com.eisgroup.hrcrm.model.Project;

import java.util.List;

public interface ProjectDAO extends BaseObjectDAO<Project> {
    // List<Project> getActiveProjectsByTaskType(String taskType);

    // List<Project> getProjectsByTaskType(String taskType);

    public List<Project> getAllActiveProjects();

    void resetToDefault();

    void setDeletedAll();

    Project getByName(String name);
}
