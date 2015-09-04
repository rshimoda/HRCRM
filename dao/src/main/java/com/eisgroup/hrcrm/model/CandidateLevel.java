package com.eisgroup.hrcrm.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class CandidateLevel extends BaseObject {
    @Column(unique = true, nullable = false)
    private String name;

    @Column
    private boolean isDeleted;

    @Column
    private boolean isDefault;

    @Column(nullable = false)
    private String taskType;

    public CandidateLevel() {
    }

    public CandidateLevel(String name, boolean isDeleted, boolean isDefault, String taskType) {
        this.name = name;
        this.isDeleted = isDeleted;
        this.isDefault = isDefault;
        this.taskType = taskType;
    }

    public CandidateLevel(long id, String name, boolean isDeleted, boolean isDefault, String taskType) {
        this.name = name;
        this.isDeleted = isDeleted;
        this.isDefault = isDefault;
        this.taskType = taskType;
        setId(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }
}
