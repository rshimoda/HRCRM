package com.eisgroup.hrcrm.ui.beans;

import com.eisgroup.hrcrm.model.*;
import com.eisgroup.hrcrm.service.PerformanceAppraisalService;
import com.eisgroup.hrcrm.service.TaskService;
import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.*;

@ManagedBean
@ViewScoped
public class PerformanceAppraisalsBean extends TaskBean implements Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(PerformanceAppraisalsBean.class);
    private static final long MAX_REVIEWERS_BY_TASK = 3;

    private boolean isUserCanAnswer;

    @ManagedProperty(value = "#{performanceAppraisalService}")
    private TaskService taskService;

    @ManagedProperty(value = "#{performanceAppraisalService}")
    private PerformanceAppraisalService performanceAppraisalService;

    private List<PerformanceAppraisalDetail> detailsList;
    private List<PerformanceAppraisalDetail> detailsListAssignee;
    private List<PerformanceAppraisalDetail> detailsListAll;
    private Map<String, List<PerformanceAppraisalDetail>> answerCompare;
    private Set<Question> setQuestion;
    private List<PerformanceAppraisalGoal> goalsList;
    private List<PerformanceAppraisalGoal> goalsListAssignee;
    private User user;

    private boolean viewCompareResults = false;
    private List<User> reviewers;
    private List<User> assigneeReviewers;

    private String newGoal;
    private User newReviewer;
    private User userAssignee;
    private List<Item[]> contestList;
    private List<ItemDetail[]> contestListDetail;

    @Override
    public TaskService getTaskService() {
        return taskService;
    }
//
//    @Override
//    public void cancelEdit() {
//        setDetailsList(performanceAppraisalService.getAllDetailsByTask((PerformanceAppraisal) getTask()));
//        super.cancelEdit();
//    }

    /**
     * Initializes class fields from http-parameters
     */
    @PostConstruct
    public void init() {
        super.setTaskService(taskService);
        super.setTaskType(PerformanceAppraisal.class.getSimpleName());
        super.init();
        setReviewers(getUserService().getAllActiveUsersSortByName());
        setTaskType(PerformanceAppraisal.class.getName());
        newReviewer = null;
        contestList = new ArrayList<Item[]>();
        contestListDetail = new ArrayList<ItemDetail[]>();

        if (getTask() == null) {
            setTask(new PerformanceAppraisal());
            getTask().setStatus(Status.OPEN);
            generateTaskDetails();
        } else {

            userAssignee = getTask().getAssignee();
            setReviewers(performanceAppraisalService.getReviewersByTask(getTask()));
            getUsers().removeAll(getReviewers());
            getUsers().remove(getTask().getAssignee());
            if (getTask().getStatus() != Status.CLOSED && getTask().getStatus() != Status.RESOLVED) {
                setIsUserCanAnswer(performanceAppraisalService.isUserCanAnswer(getTask(), getUser()));
            } else {
                setIsUserCanAnswer(false);
            }
            setDetailsList(loadDetails(getUser()));
            setDetailsListAssignee(loadDetails(userAssignee));
            setGoalsList(loadGoals(getUser()));
            setGoalsListAssignee(loadGoals(userAssignee));

            if (getTask().getResolution() != null && !"Unresolved".equals(getTask().getResolution().getName())) {
                setViewCompareResults(true);
            }

            initCompare();

            for (Map.Entry entry : answerCompare.entrySet()) {
                String key = (String) entry.getKey();
                LOG.info(key + "   ");
                List<PerformanceAppraisalDetail> value = (List<PerformanceAppraisalDetail>) entry.getValue();
                for (PerformanceAppraisalDetail list : value) {
                    LOG.info(list.getAnswer() + " ");
                }
            }
            LOG.info(Integer.toString(answerCompare.size()));
            if (getUser() != null) {
                compareResults();
            }
        }


    }

    public void compareResults() {
        assigneeReviewers = new ArrayList<User>();
        assigneeReviewers.add(userAssignee);
        for (User user1 : reviewers) {
            assigneeReviewers.add(user1);
        }

        Item item;

        for (int i = 0; i < loadGoals(userAssignee).size(); i++) {
            Item[] child = new Item[assigneeReviewers.size()];
            for (int j = 0; j < assigneeReviewers.size(); j++) {
                item = new Item();
                String itemValue = loadGoals(assigneeReviewers.get(j)).get(i).getGoalAnswer();
                if (itemValue == null) {
                    item.setValue("-");
                } else {
                    item.setValue(itemValue);

                }
//                item.setValue(loadGoals(reviewers.get(j)).get(i).getGoalAnswer());
                child[j] = item;
            }
            contestList.add(child);


        }


        ItemDetail itemDetail;
        for (int i = 0; i < loadDetails(userAssignee).size(); i++) {
            ItemDetail[] childDetail = new ItemDetail[assigneeReviewers.size()];
            for (int j = 0; j < assigneeReviewers.size(); j++) {
                itemDetail = new ItemDetail();
                String itemValue = loadDetails(assigneeReviewers.get(j)).get(i).getAnswer();
                if (itemValue == null) {
                    itemDetail.setValue("-");
                } else {
                    itemDetail.setValue(itemValue);
                }
//                itemDetail.setValue(loadDetails(getReviewers().get(j)).get(i).getAnswer());
                childDetail[j] = itemDetail;
            }
            contestListDetail.add(childDetail);
        }


    }

    public Map<String, List<PerformanceAppraisalDetail>> initCompare() {
        detailsListAll = performanceAppraisalService.getAllDetailsByTask(getTask());

        setQuestion = new HashSet<Question>();
        answerCompare = new HashMap<String, List<PerformanceAppraisalDetail>>();

        for (PerformanceAppraisalDetail value : detailsListAll) {
            setQuestion.add(value.getQuestion());
        }


        for (Question quest : setQuestion) {
            LOG.info(quest.getName() + "  ");
            List<PerformanceAppraisalDetail> listValue = new ArrayList<PerformanceAppraisalDetail>();
            for (PerformanceAppraisalDetail list : detailsListAll) {
                if (quest.getName().equals(list.getQuestion().getName())) {
                    listValue.add(list);
                }
                answerCompare.put(quest.getName(), listValue);
            }
        }
        return answerCompare;
    }


    private List<PerformanceAppraisalDetail> loadDetails(User user) {
        List<PerformanceAppraisalDetail> details;
        if (!performanceAppraisalService.isUserCanAnswer(getTask(), user)) {
            details = performanceAppraisalService.getAllDetailsByTaskUser(getTask(), null);

        } else {
            details = performanceAppraisalService.getAllDetailsByTaskUser(getTask(), user);
        }
        return details;
    }

    private List<PerformanceAppraisalGoal> loadGoals(User user) {
        List<PerformanceAppraisalGoal> goals;
        if (!performanceAppraisalService.isUserCanAnswer(getTask(), user)) {
            goals = performanceAppraisalService.getAllGoalsByTaskUser(getTask(), null);

        } else {
            goals = performanceAppraisalService.getAllGoalsByTaskUser(getTask(), user);
        }
        return goals;
    }

    public void addGoal() {
        ResourceBundle resourceBundle = PropertyResourceBundle.getBundle("messages/messages");
        if ("".equals(newGoal.replaceAll("\\s", "")) || newGoal == null) {
            FacesContext.getCurrentInstance().addMessage("addGoalForm:newGoal", new FacesMessage(FacesMessage.SEVERITY_ERROR, "", resourceBundle.getString("TaskValidation.GoalEmpty")));
            return;
        }

        newGoal = newGoal.trim().replaceAll("\\s+", " ");

        if (!getGoalsList().isEmpty()) {
            for (PerformanceAppraisalGoal goal : getGoalsList()) {
                if (goal.getGoal().equalsIgnoreCase(newGoal)) {
                    FacesContext.getCurrentInstance().addMessage("addGoalForm:newGoal", new FacesMessage(FacesMessage.SEVERITY_ERROR, "", resourceBundle.getString("TaskValidation.GoalExist")));
                    return;
                }
            }
        }

        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('addGoalDlg').hide();");

        if (getGoalsList() == null) {
            setGoalsList(new ArrayList<PerformanceAppraisalGoal>());
        }

        PerformanceAppraisalGoal goal = new PerformanceAppraisalGoal();
        goal.setGoal(newGoal);
        goal.setPerformanceAppraisal(getTask());
        performanceAppraisalService.createGoal(goal);

        List<User> users = performanceAppraisalService.getUsersByTask(getTask());
        newGoal = null;
        if (users == null) {
            return;
        }

        for (User userr : users) {
            goal.setUser(userr);
            goal.setId(null);
            performanceAppraisalService.createGoal(goal);
        }

        setGoalsList(loadGoals(getUser()));
    }

    @Override
    public void assigneeUpdate() {
        if (getTask().getAssignee() != null) {
            return;
        }

        if (reviewers.contains(getTask().getAssignee())) {
            init();
        }
        super.assigneeUpdate();
        initPAUser(getTask().getAssignee(), false);
        init();

    }

    @Override
    public void resolutionUpdate() {
        super.resolutionUpdate();
        initPAUser(getTask().getAssignee(), false);
        setViewCompareResults(true);
        init();

    }

    private void initPAUser(User paUser, boolean isReviewer) {
        if (performanceAppraisalService.isUserCanAnswer(getTask(), paUser)) {
            return;
        }
        PerformanceAppraisalUser appraisalUser = new PerformanceAppraisalUser();

        appraisalUser.setAppraisalUser(paUser);
        appraisalUser.setPerformanceAppraisal(getTask());
        appraisalUser.setReviewer(isReviewer);
        performanceAppraisalService.createPAUser(appraisalUser);
        generateUserDetails(paUser);
        generateUserGoals(paUser);

    }

    @Override
    public void finishUpdate() {
        super.finishUpdate();
        initPAUser(getTask().getAssignee(), false);
        init();


    }

    public void chooseAssignee() {
        setReviewers(getUserService().getAllActiveUsersSortByName());
        getReviewers().remove(getTask().getAssignee());
    }

    public void chooseReviewer() {
        setUsers(getUserService().getAllActiveUsersSortByName());
        getUsers().remove(newReviewer);
    }


    public void addReviewer() {
        if (newReviewer.equals(getTask().getAssignee()) || reviewers.contains(newReviewer)) {
            return;
        }
        initPAUser(newReviewer, true);


        RequestContext.getCurrentInstance().execute("PF('setr1').hide();");
        newReviewer = null;
        init();


    }

    public void validateMaxReviewers(FacesContext context, javax.faces.component.UIComponent component, Object value) throws javax.faces.validator.ValidatorException {
        ResourceBundle resourceBundle = PropertyResourceBundle.getBundle("messages/messages");
        if (performanceAppraisalService.getReviewersByTask(getTask()).size() > MAX_REVIEWERS_BY_TASK) {
            throw new javax.faces.validator.ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "", resourceBundle.getString("TaskValidation.maxReviewers")));
        }
    }

    private void generateUserDetails(User user) {

        List<PerformanceAppraisalDetail> details = performanceAppraisalService.getAllDetailsByTaskUser(getTask(), null);
        if (details == null || user == null) {
            return;
        }
        for (PerformanceAppraisalDetail detail : details) {
            detail.setUser(user);
            detail.setId(null);
            performanceAppraisalService.createDetail(detail);
        }
    }

    private void generateUserGoals(User user) {
        List<PerformanceAppraisalGoal> goals = performanceAppraisalService.getAllGoalsByTaskUser(getTask(), null);
        if (goals == null || user == null) {
            return;
        }
        for (PerformanceAppraisalGoal goal : goals) {
            goal.setUser(user);
            goal.setId(null);
            performanceAppraisalService.createGoal(goal);
        }
    }

    /**
     * Overrides root method to get specified TaskClass type
     *
     * @return PerformanceAppraisal
     */
    @Override
    public PerformanceAppraisal getTask() {
        return (PerformanceAppraisal) super.getTask();
    }

    /**
     * Overrides root method to support TaskClass specified redirection
     */
    @Override
    public void create() {
        getTask().setComplexity(getTaskPropertyService().getComplexityById((long) 1));
        getTask().setPriority(getPriorities()[0]);
        super.create();
        performanceAppraisalService.saveDetails(detailsList);
        if (getTask().getAssignee() != null) {
            initPAUser(getTask().getAssignee(), false);
        }

        if (newReviewer != null && !newReviewer.equals(getTask().getAssignee())) {
            initPAUser(newReviewer, true);
        }

        if (getTask().getCreator() != null && getTask().getCreator() != newReviewer) {
            initPAUser(getTask().getCreator(), true);
        }
    }

    @Override
    public void reopen() {
        super.reopen();
        setViewCompareResults(false);
        setIsUserCanAnswer(performanceAppraisalService.isUserCanAnswer(getTask(), getUser()));

    }

    @Override
    public void closeTask() {
        super.closeTask();
        setIsUserCanAnswer(false);

    }

    public void saveDetailAnswers() {
        LOG.info("save details");
        performanceAppraisalService.saveDetails(detailsList);
    }

    public void saveGoalAnswers() {
        performanceAppraisalService.saveGoals(goalsList);
    }

    /**
     * Overrides root method to support injection of TaskClass specified service
     *
     * @param taskService
     */
    @Override
    public void setTaskService(TaskService taskService) {
        super.setTaskService(taskService);
        this.taskService = taskService;
    }

    public List<PerformanceAppraisalDetail> getDetailsList() {
        return detailsList;
    }

    public void setDetailsList(List<PerformanceAppraisalDetail> detailsList) {
        this.detailsList = detailsList;
    }

    private void generateTaskDetails() {
        setDetailsList(new ArrayList<PerformanceAppraisalDetail>());
        List<Question> questions = performanceAppraisalService.getAllActiveQuestions();
        if (questions == null || questions.isEmpty()) {
            return;
        }
        for (Question question : questions) {
            PerformanceAppraisalDetail details = new PerformanceAppraisalDetail();
            details.setQuestion(question);
            details.setPerformanceAppraisal(getTask());
            detailsList.add(details);
        }
    }

    public void setPerformanceAppraisalService(PerformanceAppraisalService performanceAppraisalService) {
        this.performanceAppraisalService = performanceAppraisalService;
    }

    public List<PerformanceAppraisalGoal> getGoalsList() {
        return goalsList;
    }

    public void setGoalsList(List<PerformanceAppraisalGoal> goalsList) {
        this.goalsList = goalsList;
    }

    public boolean isUserCanAnswer() {
        return isUserCanAnswer;
    }

    public void setIsUserCanAnswer(boolean isUserCanAnswer) {
        this.isUserCanAnswer = isUserCanAnswer;
    }

    public String getNewGoal() {
        return newGoal;
    }

    public void setNewGoal(String newGoal) {
        this.newGoal = newGoal;
    }

    public User getNewReviewer() {
        return newReviewer;
    }

    public void setNewReviewer(User newReviewer) {
        this.newReviewer = newReviewer;
    }

    public List<User> getReviewers() {
        return reviewers;
    }

    public void setReviewers(List<User> reviewers) {
        this.reviewers = reviewers;
    }

    public Map<String, List<PerformanceAppraisalDetail>> getAnswerCompare() {
        return answerCompare;
    }

    public void setAnswerCompare(Map<String, List<PerformanceAppraisalDetail>> answerCompare) {
        this.answerCompare = answerCompare;
    }

    public User getUserAssignee() {
        return userAssignee;
    }

    public void setUserAssignee(User userAssignee) {
        this.userAssignee = userAssignee;
    }

    public List<Item[]> getContestList() {
        return contestList;
    }

    public void setContestList(List<Item[]> contestList) {
        this.contestList = contestList;
    }

    public List<ItemDetail[]> getContestListDetail() {
        return contestListDetail;
    }

    public void setContestListDetail(List<ItemDetail[]> contestListDetail) {
        this.contestListDetail = contestListDetail;
    }

    public List<PerformanceAppraisalGoal> getGoalsListAssignee() {
        return goalsListAssignee;
    }

    public List<PerformanceAppraisalDetail> getDetailsListAssignee() {
        return detailsListAssignee;
    }

    public void setDetailsListAssignee(List<PerformanceAppraisalDetail> detailsListAssignee) {
        this.detailsListAssignee = detailsListAssignee;
    }

    public void setGoalsListAssignee(List<PerformanceAppraisalGoal> goalsListAssignee) {
        this.goalsListAssignee = goalsListAssignee;

    }

    public List<PerformanceAppraisalDetail> getDetailsListAll() {
        return detailsListAll;
    }

    public void setDetailsListAll(List<PerformanceAppraisalDetail> detailsListAll) {
        this.detailsListAll = detailsListAll;
    }

    public List<User> getAssigneeReviewers() {
        return assigneeReviewers;
    }

    public void setAssigneeReviewers(List<User> assigneeReviewers) {
        this.assigneeReviewers = assigneeReviewers;
    }


    public class Item {
        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public class ItemDetail {
        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public boolean isViewCompareResults() {
        return viewCompareResults;
    }

    public void setViewCompareResults(boolean viewCompareResults) {
        this.viewCompareResults = viewCompareResults;
    }


}
