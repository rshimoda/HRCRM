package com.eisgroup.hrcrm.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Resolution extends BaseObject {

    @Column(unique = true, nullable = false)
    private String name;

    public Resolution() {
    }

    public Resolution(String name) {
        this.name = name;
    }

    public Resolution(long id, String name) {
        this.name = name;
        setId(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
