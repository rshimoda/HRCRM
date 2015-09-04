package com.eisgroup.hrcrm.ui.beans;

import com.eisgroup.hrcrm.model.*;
import com.eisgroup.hrcrm.service.CommentsService;
import com.eisgroup.hrcrm.service.TaskPropertyService;
import com.eisgroup.hrcrm.service.TaskService;
import com.eisgroup.hrcrm.service.UserService;
import org.primefaces.component.selectcheckboxmenu.SelectCheckboxMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

public abstract class TaskBean {

    private static final Logger LOG = LoggerFactory.getLogger(TaskBean.class);

    private Task task;
    private String taskType;
    private Comment comment;

    private User user;
    private Date date;
    private String commentValue;

    private boolean isEditMode = false;
    private boolean isViewMode = false;
    private boolean enableAddComments = false;
    private TaskService taskService;

    private List<Complexity> complexities;
    private List<Resolution> resolutions;
    private List<Resolution> resolutionValue;
    private List<Comment> comments;
    private List<CandidateLevel> candidateLevels;
    private List<Position> positions;
    private List<Project> projects;

    private List<User> users;
    private User assigneeSet;
    private Resolution resolutionSet;

    @ManagedProperty(value = "#{taskPropertyService}")
    private TaskPropertyService taskPropertyService;

    @ManagedProperty(value = "#{userService}")
    private UserService userService;

    @ManagedProperty(value = "#{commentsService}")
    private CommentsService commentsService;


    /**
     * Used to get current OdsInternal task with predefined settings to view
     *
     * @return {@link Task}
     */
    public Task getTask() {
        return task;
    }

    /**
     * Used to set current task task from view
     *
     * @param task Task - task from view
     */
    public void setTask(Task task) {
        if (task.getId() == null && task.getCreator() == null) {
            task.setCreator(getUser());
        }
        this.task = task;
    }

    /**
     * Used to get isEditMode flag
     *
     * @return isEditMode
     */
    public boolean isEditMode() {
        return isEditMode;
    }

    /**
     * Used to set isEditMode Task task
     */
    public void setEditMode(boolean isEditMode) {
        this.isEditMode = isEditMode;
    }


    public boolean isEnableAddComments() {
        return enableAddComments;
    }

    public void setEnableAddComments(boolean enableAddComments) {
        this.enableAddComments = enableAddComments;
    }

    /**
     * Used for inject service from TaskClass specified bean
     *
     * @param TaskService Spring service
     */
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Used to support call to service from TaskClass specified bean
     *
     * @return
     */
    public TaskService getTaskService() {
        return taskService;
    }

    public CommentsService getCommentsService() {
        return commentsService;
    }

    public void setCommentsService(CommentsService commentsService) {
        this.commentsService = commentsService;
    }


    /**
     * Used to activate edit mode in view task
     */
    public void startEdit() {
//        if (getTask().getStatus() != Status.CLOSED) {
        setEditMode(true);
        System.out.println("Touch edit");
//        }
    }


    /**
     * Future functionality extention to handle reopen operation
     */
    public void reopen() {
        if (isTaskIsClosed()) {
            getTask().setClosedDate(null);
            getTask().setStatus(Status.REOPENED);
            getTask().setResolution(getTaskPropertyService().getResolutionById((long) 1));
            getTask().setUpdateDate(new Date());
            taskService.update(getTask());
        }
    }

    /**
     * Handles button Cancel functionality
     */
    public void cancelEdit() {
        isEditMode = false;
        setTask(taskService.find(getTask().getId()));
    }

    /**
     * Used to update Task task Date to DB
     */
    public void finishUpdate() {
        isEditMode = false;
        task.setUpdateDate(new Date());
        taskService.update(task);
        redirectToTask();
    }

    /**
     * Used to manage create action on task creation page
     */
    public void create() {
        getTask().setCreationDate(new Date());
        getTask().setUpdateDate(getTask().getCreationDate());
        getTask().setResolution(getTaskPropertyService().getResolutionById((long) 1));
        taskService.create(getTask());
        redirectToTask();

    }

    public void redirectToTask() {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        try {
            context.redirect(context.getRequestContextPath() + "/browse/task-" + getTask().getId());
        } catch (IOException e) {
            LOG.info("redirect exception: ", e);
        }
    }

    /**
     * Used to handle close task operation and save changes to DB
     */
    public void closeTask() {
        if (getTask().getStatus() != Status.CLOSED) {
            getTask().setClosedDate(new Date());
            getTask().setStatus(Status.CLOSED);
            taskService.update(getTask());
        }
    }

    /**
     * Used to get taskType of TaskClass bean implementation
     *
     * @return String taskType
     */
    public String getTaskType() {
        return taskType;
    }

    /**
     * Used to set taskType of TaskClass in bean implementation
     *
     * @param taskType
     */
    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    /**
     * Used to manage EditMode or CreationMode mode of bean functional
     *
     * @return boolean isViewMode
     */
    public boolean isViewMode() {
        return isViewMode;
    }

    /**
     * Used to manage EditMode or CreationMode mode of bean functional
     *
     * @param boolean isViewMode
     */
    public void setViewMode(boolean isViewMode) {
        this.isViewMode = isViewMode;
    }

    /**
     * Used to handle task close status
     *
     * @return boolean taskIsClosed
     */
    public boolean isTaskIsClosed() {
        return task.getStatus().equals(Status.CLOSED);
    }

    /**
     * Used to handle task close status
     *
     * @param boolean taskIsClosed
     */

    /**
     * Find all task priorities, registered in DB
     *
     * @return array of {@link Priority}
     */
    public Priority[] getPriorities() {
        return Priority.values();
    }

    /**
     * Find all task complexities, registered in DB
     *
     * @return List of {@link Complexity}
     */
    public List<Complexity> getComplexities() {
        return complexities;
    }

    /**
     * Find all task resolutions, registered in DB
     *
     * @return List of {@link Resolution}
     */
    public List<Resolution> getResolutions() {
        return resolutions;
    }

    /**
     * Find all task statuses, registered in system
     *
     * @return array of {@link Status enums}
     */
    public Status[] getStatuses() {

        return Status.values();
    }

    /**
     * Find all task sources, registered in system
     *
     * @return array of {@link Source enums}
     */
    public Source[] getSources() {

        return Source.values();
    }

    /**
     * Init method to create all needed environment
     */
    public void init() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String login = externalContext.getUserPrincipal().getName();
        user = getUserService().findUserByLogin(login);

        String taskId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("taskId");
        if (taskId != null && !taskId.isEmpty()) {
            try {
                Task task = getTaskService().find(Long.valueOf(taskId));
                if (task == null) {
                    return;
                }
                setTask(task);
            } catch (NumberFormatException e) {
                LOG.info("init exception: ");
            }
            if (getTask() != null) {
                setViewMode(true);
            }
        }

        if (getTask() != null || taskId == null || taskId.isEmpty()) {
            resolutions = taskPropertyService.getAllResolutions();
            resolutionValue = taskPropertyService.getResolutionValue();
            complexities = taskPropertyService.getActiveComplexitiesByTaskType(taskType);
            candidateLevels = taskPropertyService.getActiveCandidateLevelsByTaskType(taskType);
            projects = taskPropertyService.getAllActiveProjects();
            positions = taskPropertyService.getActivePositionsByTaskType(taskType);
            users = userService.getAllActiveUsersSortByName();
            //getUsers().remove(user);
        }

        comments = commentsService.getAllCommentsByTask(task);
        comment = new Comment();
    }


    public void setTaskPropertyService(TaskPropertyService taskPropertyService) {
        this.taskPropertyService = taskPropertyService;
    }

    public String fiterViewTasks() {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        sessionMap.put("filteredTaskType", task.getClass().getSimpleName());
        return "ViewTasks";
    }

    public TaskPropertyService getTaskPropertyService() {
        return taskPropertyService;
    }

    /**
     * Find all candidate levels, registered in system
     *
     * @return List of {@link CandidateLevel}
     */
    public List<CandidateLevel> getCandidateLevels() {
        return candidateLevels;
    }

    /**
     * Find all positions, registered in system
     *
     * @return List of {@link Position}
     */
    public List<Position> getPositions() {
        return positions;
    }

    /**
     * Find all projects, registered in system
     *
     * @return List of {@link Project}
     */
    public List<Project> getProjects() {
        return projects;
    }

    /**
     * Used to get current dates for calendars in Creation screens.
     *
     * @author OAlieksienko
     */
    public Date getCurrentDate() {
        return new Date();
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

    public void setCandidateLevels(List<CandidateLevel> candidateLevels) {
        this.candidateLevels = candidateLevels;
    }

    public void setComplexities(List<Complexity> complexities) {
        this.complexities = complexities;
    }

    public void setResolutions(List<Resolution> resolutions) {
        this.resolutions = resolutions;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public User getAssigneeSet() {
        return assigneeSet;
    }

    public void setAssigneeSet(User assigneeSet) {
        this.assigneeSet = assigneeSet;
    }

    public Resolution getResolutionSet() {
        return resolutionSet;
    }

    public void setResolutionSet(Resolution resolutionSet) {
        this.resolutionSet = resolutionSet;
    }

    public List<Resolution> getResolutionValue() {
        return resolutionValue;
    }

    public void setResolutionValue(List<Resolution> resolutionValue) {
        this.resolutionValue = resolutionValue;
    }

    public String getCommentValue() {
        return commentValue;
    }

    public void setCommentValue(String commentValue) {
        this.commentValue = commentValue;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addComment() {
        if ("".equals(commentValue)) {
            init();
            commentValue = null;
        } else {
            comment.setComment(commentValue);
            date = new Date();
            comment.setTask(task);
            Timestamp ts = new Timestamp(date.getTime());
            comment.setDate(ts);
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String login = externalContext.getUserPrincipal().getName();
            user = getUserService().findUserByLogin(login);
            comment.setUser(user);
            commentsService.create(comment);
            init();
            commentValue = null;
        }

    }

    public void assigneeUpdate() {
        task.setAssignee(assigneeSet);
        task.setUpdateDate(new Date());
        setAssigneeSet(null);
        taskService.update(task);
    }

    public void resolutionUpdate() {
        task.setResolution(resolutionSet);
        task.setUpdateDate(new Date());
        setResolutionSet(null);
        getTask().setStatus(Status.RESOLVED);
        taskService.update(task);
        addComment();
    }

    public void resetSets() {
        setAssigneeSet(null);
        setResolutionSet(null);
    }

    public String getAssigneeFullName() {
        if (task.getAssignee() != null)
            return task.getAssignee().getFullName();
        else
            return "Unassigned";
    }

    public void startAddComments() {
        setEnableAddComments(true);
    }

    public void addCommentFromArea() {
        addComment();
        setEnableAddComments(false);
    }

    public void commentCancel() {
        commentValue = null;
        setEnableAddComments(false);
    }

    public String updateLinkButton() {
        if ("Resolved".equals(getTask().getStatus().getName()) || "Closed".equals(getTask().getStatus().getName())) {
            return "true";
        }
        return "false";
    }

    public void updateLabel(String inputId) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SelectCheckboxMenu ddl = null;
        if ("filterLevelRecruitment".equals(inputId) || "filterPositionRecruitment".equals(inputId)) {
            ddl = (SelectCheckboxMenu) facesContext.getViewRoot().findComponent(":LinkToCandidateForm:activeCandidatesTable:" + inputId);
        } else if ("filterPositionCandidate".equals(inputId) || "filterLevelCandidate".equals(inputId)) {
            ddl = (SelectCheckboxMenu) facesContext.getViewRoot().findComponent(":LinkToVacancyForm:activeRecruitmentsTable:" + inputId);
        }
        String[] values = (String[]) ddl.getValue();
        String label;

        if (values != null && values.length == 1) {
            label = values[0];
        } else if (values.length > 1) {
            label = "Multiple";
        } else label = null;
        ddl.setLabel(label);

    }
}
