package com.eisgroup.hrcrm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
public class Project extends BaseObject {
    @Column(unique = true, nullable = false)
    private String name;

    @Column
    private boolean isDeleted;

    @Column
    private boolean isDefault;

    @ManyToMany(mappedBy = "projects")
    private List<User> users;

    public Project() {
    }

    public Project(String name, boolean isDeleted, boolean isDefault, List<User> users) {
        this.name = name;
        this.isDeleted = isDeleted;
        this.isDefault = isDefault;
        this.users = users;
    }

    public Project(long id, String name, boolean isDeleted, boolean isDefault, List<User> users) {
        this.name = name;
        this.isDeleted = isDeleted;
        this.isDefault = isDefault;
        this.users = users;
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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

}
