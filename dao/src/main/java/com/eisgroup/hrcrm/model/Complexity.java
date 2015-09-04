package com.eisgroup.hrcrm.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Complexity extends BaseObject {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String taskType;

    @Column
    private boolean isDeleted;

    @Column
    private boolean isDefault;

    public Complexity() {
    }

    public Complexity(String name, String taskType, boolean isDeleted, boolean isDefault) {
        this.name = name;
        this.taskType = taskType;
        this.isDeleted = isDeleted;
        this.isDefault = isDefault;
    }

    public Complexity(long id, String name, String taskType, boolean isDeleted, boolean isDefault) {
        this.name = name;
        this.taskType = taskType;
        this.isDeleted = isDeleted;
        this.isDefault = isDefault;
        setId(id);
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String tasktype) {
        this.taskType = tasktype;
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

}
