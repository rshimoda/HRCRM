package com.eisgroup.hrcrm.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "QUESTIONS")
public class Question extends BaseObject {

    @Column(unique = true, nullable = false)
    private String name;

    @Column
    private boolean isDeleted;

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

}
