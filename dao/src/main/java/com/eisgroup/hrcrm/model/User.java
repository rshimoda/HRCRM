package com.eisgroup.hrcrm.model;

import javax.persistence.*;
import java.util.List;

@Entity(name = "USERS")
public class User extends BaseObject {

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String login;

    @Column
    private String password;

    @Column
    private String email;

    @Column(name = "USER_PRIVILEGES")
    private String privileges;

    @Column
    private String description;

    @Column
    private boolean isDeleted;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "USER_PROJECT",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "PROJECT_ID", referencedColumnName = "ID")})
    private List<Project> projects;

    public User() {

    }

    public User(String firstName, String lastName, String login, String password, String email, String privileges,
                String description, boolean isDeleted, List<Project> projects) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.email = email;
        this.privileges = privileges;
        this.description = description;
        this.isDeleted = isDeleted;
        this.projects = projects;
    }

    public User(long id, String firstName, String lastName, String login, String password, String email,
                String privileges, String description, boolean isDeleted, List<Project> projects) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.email = email;
        this.privileges = privileges;
        this.description = description;
        this.isDeleted = isDeleted;
        this.projects = projects;
        setId(id);
    }

    //----------------- Equals and HashCode -----------------

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof User) || !super.equals(o))
            return false;

        User that = (User) o;

        if (!email.equals(that.email) || !login.equals(that.login))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + login.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }


    // ---------------- Getters and Setters -----------------

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPrivileges() {
        return privileges;
    }

    public void setPrivileges(String privileges) {
        this.privileges = privileges;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
