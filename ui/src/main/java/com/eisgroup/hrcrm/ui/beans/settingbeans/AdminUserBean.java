package com.eisgroup.hrcrm.ui.beans.settingbeans;

import com.eisgroup.hrcrm.model.Project;
import com.eisgroup.hrcrm.model.User;
import com.eisgroup.hrcrm.service.UserService;
import com.eisgroup.hrcrm.ui.validators.EmailValidator;
import com.eisgroup.hrcrm.ui.validators.UserNameValidator;
import org.primefaces.context.RequestContext;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

@ManagedBean
@ViewScoped
public class AdminUserBean extends SettingsBean implements Serializable {

    private List<User> users;
    private User user;
    private boolean isUserEditMode;
    private List<Project> projects;
    private Project project;


    @ManagedProperty(value = "#{userService}")
    private UserService userService;

    /**
     * Method for initializing needed environment
     */
    @PostConstruct
    public void init() {
        RequestContext.getCurrentInstance().reset("tabsView:createUserForm");
        project = null;
        super.setEditMode(false);
        setUserEditMode(false);
        setUsers(userService.getAllActiveUsers());
        setProjects(taskPropertyService.getAllActiveProjects());

    }

    /**
     * Gets list of all users in DB
     *
     * @return list of users
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * Sets list of users that will be shown in table of users
     *
     * @param users
     */
    public void setUsers(List<User> users) {
        this.users = users;
    }

    /**
     * Injects service for manipulating users
     *
     * @param userService
     */
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Used to get all projects to JSF view, registered in system
     *
     * @return
     */
    public List<Project> getProjects() {
        return projects;
    }

    /**
     * Used to inject projects in init()
     *
     * @param projects
     */
    private void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    /**
     * Used to handle Create/Edit user operation
     *
     * @return
     */
    public boolean isUserEditMode() {
        return isUserEditMode;
    }


    /**
     * Used to handle Create/Edit user operation
     *
     * @return
     */
    private void setUserEditMode(boolean isUserEditMode) {
        this.isUserEditMode = isUserEditMode;
    }

    /**
     * Method for start creating new user
     */
    public void startCreatingUser() {
        setUser(new User());
        getUser().setProjects(new ArrayList<Project>());
        setUserEditMode(false);
        setEditMode(true);
    }

    /**
     * Method for start editing existing user
     */
    public void startEdit(User user) {
        user.setPassword(null);
        setUser(user);
        setUserEditMode(true);
        setEditMode(true);
    }

    /**
     * Used to save user to DB, trims FirstName, LastName, login and email before save
     */
    public void save() {
//        if (!isUniqueLoginAndEmail()) {
//            return;
//        }
        if (getUser().getProjects().size() > 1) {
            getUser().getProjects().add(0, project);
        } else {
            getUser().getProjects().add(project);
        }
        getUser().setPrivileges("su");
        getUser().setEmail(getUser().getEmail().trim());
        getUser().setFirstName(getUser().getFirstName().trim());
        getUser().setLastName(getUser().getLastName().trim());
        getUser().setLogin(getUser().getLogin().trim());
        userService.update(user);
        setUserEditMode(false);
        setEditMode(false);
        init();
    }

    /**
     * * Check if login and email are unique. Add messages if fields are not unique.
     * *
     * * @return true if unique, false if not unique
     */
    private boolean isUniqueLogin(String login) {
        User dbUser = userService.findUserByLogin(login);
        return !(dbUser != null && (getUser().getId() == null || !getUser().getId().equals(dbUser.getId())));

    }

    private boolean isUniqueEmail(String email) {
        User dbUser = userService.findUserByEmail(email);
        return !(dbUser != null && (getUser().getId() == null || !getUser().getId().equals(dbUser.getId())));
    }


    /**
     * * Method for adding message to faces context
     * *
     * * @param clientId of the component
     * * @param facesMessage message to show
     */
    private void addMessage(String clientId, FacesMessage facesMessage) {
        FacesContext.getCurrentInstance().addMessage(clientId, facesMessage);
    }

    /**
     * Used to delete user from DB and renew users list
     */
    public void deleteUser() {
        userService.delete(user);
        init();
    }

    public void loginValidator(FacesContext context, UIComponent component, Object value) {
        String login = ((String) value).trim();
        new UserNameValidator().validate(context, component, value);
        if (!isUniqueLogin(login)) {
            ResourceBundle resourceBundle = PropertyResourceBundle.getBundle("messages/messages");
//            addMessage("tabsView:createUserForm:login", new FacesMessage(FacesMessage.SEVERITY_ERROR, "", resourceBundle.getString("User.notUniqueLogin")));
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "", resourceBundle.getString("User.notUniqueLogin")));
        }
    }

    public void emailValidator(FacesContext context, UIComponent component, Object value) {
        String email = ((String) value).trim();
        new EmailValidator().validate(context, component, value);
        if (!isUniqueEmail(email)) {
            ResourceBundle resourceBundle = PropertyResourceBundle.getBundle("messages/messages");
//            addMessage("tabsView:createUserForm:email", new FacesMessage(FacesMessage.SEVERITY_ERROR, "", resourceBundle.getString("User.notUniqueEmail")));
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "", resourceBundle.getString("User.notUniqueEmail")));
        }
    }

    /**
     * Used to set user for delete before user press OK in popUp
     *
     * @param user
     */
    public void setUserForDelete(User user) {
        setUser(user);
    }

    /**
     * Used for get user for Create/Edit screen
     */

    public User getUser() {
        return user;
    }

    /**
     * Used for internal user set
     *
     * @param user
     */

    private void setUser(User user) {
        this.user = user;
    }

    /**
     * Used to get project for Edit mode
     *
     * @return
     */
    public Project getProject() {
        if (user != null && user.getProjects() != null && !user.getProjects().isEmpty()) {
            project = user.getProjects().get(0);
        }
        return project;
    }

    /**
     * Used to set project in editMode
     *
     * @param project
     */
    public void setProject(Project project) {
        this.project = project;
    }
}
