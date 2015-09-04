package com.eisgroup.hrcrm.ui.beans.settingbeans;

import com.eisgroup.hrcrm.model.PerformanceAppraisal;
import com.eisgroup.hrcrm.model.Question;
import com.eisgroup.hrcrm.service.PerformanceAppraisalService;
import com.eisgroup.hrcrm.ui.validators.QuestionValidator;
import org.primefaces.component.column.Column;
import org.primefaces.component.message.Message;
import org.primefaces.component.row.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.el.ValueExpression;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * @author spopov
 * @author myaskov
 */

@ManagedBean
@ViewScoped
public class PerformanceAppraisalSettingBean extends SettingsBean implements Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(PerformanceAppraisalSettingBean.class);

    @ManagedProperty(value = "#{performanceAppraisalService}")
    private PerformanceAppraisalService performanceAppraisalService;

    private static final int MAX_NUMBER_OF_QUESTIONS = 40;

    private boolean isPositionEditMode;
    private boolean isLevelEditMode;
    private boolean isQuestionEditMode;

    private boolean isAddQuestion;

    private String positions;

    private String levels;
    private List<Question> questions;
    private int indQuestionInEditMode; //index of question to edit

    private int numberOfQuestions;

    private int questionsToDelete;

    public void setDeleteQuestionIndex(int index) {
        LOG.info("DELETE: Index was set to " + index);
        questionsToDelete = index;
        LOG.info(" - (" + questionsToDelete + ")");
    }

    /**
     * Initializes class fields from http-parameters
     */
    @PostConstruct
    public void initialization() {
        init();
        //setQuestionEditMode(true); //need to initialize question fields
        //addCurrentQuestionsToView(); //initialize view of questions
        //setQuestionEditMode(false);
    }

    /**
     * Reset edit mode and update data for all fields
     */
    public void init() {
        super.setEditMode(false);
        positions = taskPropertyService.getPositionsInString(PerformanceAppraisal.class.getSimpleName());
        levels = taskPropertyService.getCandidateLevelsInString(PerformanceAppraisal.class.getSimpleName());
        //questions = getQuestionNamesFromList(performanceAppraisalService.getAllActiveQuestions());
        questions = performanceAppraisalService.getAllActiveQuestions();
        numberOfQuestions = questions.size();

        setIndQuestionInEditMode(-1);
        for (Question x : questions) {
            LOG.info(getQuestionIndex(x) + " : " + getQuestionNumber(x) + x.getName());
        }

        setEditMode(false);
        setPositionEditMode(false);
        setLevelEditMode(false);
        setQuestionEditMode(false);
        setIsAddQuestion(false);
    }

    /**
     * Save input fields
     */
    public void save() {

        if (isPositionEditMode()) {
            taskPropertyService.updatePositions(positions, PerformanceAppraisal.class.getSimpleName());
        }

        if (isLevelEditMode()) {
            taskPropertyService.updateCandidateLevels(levels, PerformanceAppraisal.class.getSimpleName());
        }

        if (isQuestionEditMode()) {
            //handleQuestions();  //get all questions from input fields and save to list
            LOG.info("SAVE: Questions were Handeled; There're " + questions.size() + " questions");
            performanceAppraisalService.updateQuestions(getQuestionNamesFromList(questions));  //save questions to DB
            //setQuestions(getQuestionNamesFromList(performanceAppraisalService.getAllActiveQuestions())); //update questions from DB
            setQuestions(performanceAppraisalService.getAllActiveQuestions()); //update questions from DB
            //cancelState();  //reset question's view
        }

        init();
    }

    /**
     * Reset state of view
     */
    public void cancelState() {
        if (isQuestionEditMode) {
            //deleteAllQuestionInputTextAreas();
            //addCurrentQuestionsToView();
        }
    }

    /**
     * Set specific field editable
     *
     * @param mode field that we want to edit
     */
    public void startEdit(String mode) {
        super.setEditMode(true);

        if (!isQuestionEditMode) {
            FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("tabsView"); //update tabsView
        }

        setPositionEditMode(false);
        setLevelEditMode(false);

        setQuestionEditMode(false);
        setIndQuestionInEditMode(-1);

        if ("position".equals(mode)) {
            setPositionEditMode(true);
        }
        if ("level".equals(mode)) {
            setLevelEditMode(true);
        }
        if ("question".equals(mode)) {
            setQuestionEditMode(true);
        }
    }

    /**
     * Add all questions from list to view
     *//*
    private void addCurrentQuestionsToView() {
        setNumberOfQuestions(0);
        for(String question : getQuestions()) {
            addNewQuestionInputTextArea(question);
        }
    }*/
    public void startEditQuestion(Question question) {
        startEdit("question");
        setIndQuestionInEditMode(getQuestions().indexOf(question));
    }

    public boolean isQuestionInEditMode(Question question) {
        if (isAddQuestion)
            return true;
        return isQuestionEditMode() && (getQuestions().indexOf(question) == getIndQuestionInEditMode());
    }

    public void addQuestion() {
        questions.add(new Question());
    }

    public boolean isLastQuestion(Question question) {
        return getQuestionIndex(question) == questions.size() - 1;
    }

    public void deleteQuestion() {
        Question q = questions.get(questionsToDelete);
        q.setName("");
        questions.set(questionsToDelete, q);
        LOG.info("From Now question #" + questionsToDelete + " is " + questions.get(questionsToDelete).getName());

        //save();
        //handleQuestions();  //get all questions from input fields and save to list
        LOG.info("SAVE: Questions were Handeled; There're " + questions.size() + " questions");
        performanceAppraisalService.updateQuestions(getQuestionNamesFromList(questions));  //save questions to DB
        //setQuestions(getQuestionNamesFromList(performanceAppraisalService.getAllActiveQuestions())); //update questions from DB
        setQuestions(performanceAppraisalService.getAllActiveQuestions()); //update questions from DB
        //cancelState();  //reset question's view
        init();
    }

    public boolean isFirstQuestion(Question question) {
        return getQuestionIndex(question) == 0;
    }

    public void moveQuestionUp(Question question) {
        int toIndex = getQuestionIndex(question) - 1;
        LOG.info("MOVE: Move mode is UP");
        LOG.info("MOVE: Current question#" + getQuestionIndex(question) + " will be moved to #" + toIndex);

        Question to = questions.get(toIndex);

        questions.set(getQuestionIndex(question), to);
        questions.set(toIndex, question);
        LOG.info("Exchange Completed");

        LOG.info("SAVE: Questions were Handeled; There're " + questions.size() + " questions");
        performanceAppraisalService.updateQuestions(getQuestionNamesFromList(questions));  //save questions to DB
        //setQuestions(getQuestionNamesFromList(performanceAppraisalService.getAllActiveQuestions())); //update questions from DB
        setQuestions(performanceAppraisalService.getAllActiveQuestions()); //update questions from DB
        //cancelState();  //reset question's view
        init();

    }

    public void moveQuestionDown(Question question) {
        int toIndex = getQuestionIndex(question) + 1;
        LOG.info("MOVE: Move mode is DOWN");
        LOG.info("MOVE: Current question#" + getQuestionIndex(question) + " will be moved to #" + toIndex);

        Question to = questions.get(toIndex);

        questions.set(getQuestionIndex(question), to);
        questions.set(toIndex, question);
        LOG.info("Exchange Completed");

        LOG.info("SAVE: Questions were Handeled; There're " + questions.size() + " questions");
        performanceAppraisalService.updateQuestions(getQuestionNamesFromList(questions));  //save questions to DB
        //setQuestions(getQuestionNamesFromList(performanceAppraisalService.getAllActiveQuestions())); //update questions from DB
        setQuestions(performanceAppraisalService.getAllActiveQuestions()); //update questions from DB
        //cancelState();  //reset question's view
        init();

    }

    /**
     * Adds question to view dynamically and adds "Edit" and "Delete" Buttons for each one
     * Also check whether user wants to enter more than MAX_NUMBER_OF_QUESTIONS he will get error message.
     *
     * @param question question that we want to add to view
     */
    public void addNewQuestionInputTextArea(final String question) {
        if (isQuestionEditMode || getQuestions().isEmpty()) {
            if (getNumberOfQuestions() <= (MAX_NUMBER_OF_QUESTIONS - 1)) {
                UIComponent questionsPanel = FacesContext.getCurrentInstance().getViewRoot().findComponent("tabsView:tabViewTasksSettings:viewEditPerformanceAppraisalSettingForm:questionsPerformanceAppraisalPanel");
                ResourceBundle resourceBundle = PropertyResourceBundle.getBundle("messages/messages");
//
//                    /*****************************QUESTION*TEXT*AREA***************************************************/
//                    OutputLabel outputLabel = new OutputLabel();
//                    outputLabel.setValue(String.format("%d. %s", questions.indexOf(question) + 1, question));
//                    outputLabel.setValueExpression("rendered",
//                            createValueExpression("#{!performanceAppraisalSettingBean.questionEditMode}", boolean.class));
//                    outputLabel.setStyle("width:310px;height:auto"); //width:335px - default
//
//                    InputTextarea inputTextarea = new InputTextarea();
//                    inputTextarea.setId("question" + getNumberOfQuestions());
//                    inputTextarea.setRows(1);
//                    inputTextarea.setCols(56);
//                    inputTextarea.setStyle("width:310px;height:auto"); //width: 335px - default
//                    inputTextarea.setValueExpression("rendered",
//                            createValueExpression("#{performanceAppraisalSettingBean.questionEditMode}", boolean.class));
//                    inputTextarea.setValue(question);
//                    inputTextarea.addValidator(new QuestionValidator());
//
//                    AjaxBehavior ajaxBehavior = new AjaxBehavior();
//                    ajaxBehavior.setUpdate("questionMessage" + getNumberOfQuestions() + ", performanceAppraisalSettingSave");
//                    inputTextarea.addClientBehavior("keyup", ajaxBehavior);
//                    /**************************************************************************************************/
//
//                    column.getChildren().add(outputLabel);
//                    column.getChildren().add(inputTextarea);
//
//                    row.getChildren().add(column);
//
//                    questionsPanel.getChildren().add(row);

                Row rowMessage = new Row();
                Column columnMessage = new Column();
                columnMessage.setStyle("height:30px!important");
                Message message = new Message();
                message.setFor("question" + getNumberOfQuestions());
                message.setId("questionMessage" + getNumberOfQuestions());
                message.setDisplay("text");
                columnMessage.getChildren().add(message);

                rowMessage.getChildren().add(columnMessage);

                questionsPanel.getChildren().add(rowMessage);

                setNumberOfQuestions(getNumberOfQuestions() + 1); //add one more question | need to iterate all questions and check max of questions
            } else {
                ResourceBundle resourceBundle = PropertyResourceBundle.getBundle("messages/messages");
                String lastQuestionId = "question" + (MAX_NUMBER_OF_QUESTIONS - 1);
                FacesContext.getCurrentInstance().addMessage("tabsView:tabViewTasksSettings:viewEditPerformanceAppraisalSettingForm:" + lastQuestionId,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "", resourceBundle.getString("AdminTask.PerformanceAppraisal.MaxNumberQuestions")));
            }
        }
    }

    public QuestionValidator getQuestionValidator() {
        return new QuestionValidator();
    }

    /**
     * Delete all question's fields from view
     */
    private void deleteAllQuestionInputTextAreas() {
        UIComponent panel = FacesContext.getCurrentInstance().getViewRoot().findComponent("tabsView:tabViewTasksSettings:viewEditPerformanceAppraisalSettingForm:questionsPerformanceAppraisalPanel");
        panel.getChildren().clear();
    }

    /*
    private void handleQuestions() {
        setQuestions(new LinkedList<String>()); //delete all questions from list

        UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
        UIComponent form = viewRoot.findComponent("tabsView:tabViewTasksSettings:viewEditPerformanceAppraisalSettingForm");
//        for (int i = getQuestions().size(); i < getNumberOfQuestions(); ++i) {
        for (int i = 0; i < questions.size(); i++) {
            InputTextarea d = (InputTextarea) form.findComponent(questions.get(i));
            if (!"".equals(String.valueOf(d.getValue()).trim())) {
                String newQuestion = String.valueOf(d.getValue()).trim();
                getQuestions().add(newQuestion);
            }
        }
    }*/

    /**
     * Get names of questions
     *
     * @param questionList List of (@link com.eisgroup.hrcrm.model.Question)
     * @return List of questions
     */
    private List<String> getQuestionNamesFromList(List<Question> questionList) {
        List<String> questionNames = new LinkedList<>();
        for (Question question : questionList) {
            questionNames.add(question.getName());
        }
        return questionNames;
    }

    /**
     * Create value expression
     *
     * @param valueExpression string of value expression type
     * @param valueType       class that belongs to type of VE
     * @return ValueExpression
     */
    private ValueExpression createValueExpression(String valueExpression, Class<?> valueType) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        return facesContext.getApplication().getExpressionFactory().createValueExpression(
                facesContext.getELContext(), valueExpression, valueType);
    }

    /* ---------------- getters and setters of edit mode ---------------- */

    /**
     * Get all (not empty) questions from input fields and add them to list
     */

    public boolean isAddQuestion() {
        return isAddQuestion;
    }

    public void setIsAddQuestion(boolean isAddQuestion) {
        this.isAddQuestion = isAddQuestion;
    }

    public boolean isPositionEditMode() {
        if (!super.isEditMode() && isPositionEditMode) {
            init();
        }
        return isPositionEditMode;
    }

    public String getQuestionNumber(Question question) {
        return String.valueOf(questions.indexOf(question) + 1) + ". ";
    }

    public int getQuestionIndex(Question question) {
        return questions.indexOf(question);
    }

    public void setPositionEditMode(boolean isPositionEditMode) {
        this.isPositionEditMode = isPositionEditMode;
    }

    public boolean isLevelEditMode() {
        if (!super.isEditMode() && isLevelEditMode) {
            init();
        }
        return isLevelEditMode;
    }

    public void setLevelEditMode(boolean isLevelEditMode) {
        this.isLevelEditMode = isLevelEditMode;
    }

    public boolean isQuestionEditMode() {
        if (!super.isEditMode() && isQuestionEditMode) {
            init();
        }
        return isQuestionEditMode;
    }

    public void setQuestionEditMode(boolean isQuestionEditMode) {
        this.isQuestionEditMode = isQuestionEditMode;
    }

    /* ---------------- getters and setters of fields ---------------- */

    public String getQuestionIndexToString(String question) {
        return String.valueOf(questions.indexOf(question));
    }

    public int getQuestionsToDelete() {
        return questionsToDelete;
    }

    public void setQuestionsToDelete(int questionToDelete) {
        LOG.info("INFO PrformanceAppraisalSettingBean:416 - Question to delete was set to " + questionToDelete);
        this.questionsToDelete = questionToDelete;
    }

    public String getPositions() {
        return positions;
    }

    public void setPositions(String positions) {
        this.positions = positions;
    }

    public String getLevels() {
        return levels;
    }

    public void setLevels(String levels) {
        this.levels = levels;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public String getStringOfNumberOfQuestions() {
        return String.valueOf(numberOfQuestions);
    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public void setPerformanceAppraisalService(PerformanceAppraisalService performanceAppraisalService) {
        this.performanceAppraisalService = performanceAppraisalService;
    }

    public PerformanceAppraisalService getPerformanceAppraisalService() {
        return this.performanceAppraisalService;
    }


    public int getIndQuestionInEditMode() {
        return indQuestionInEditMode;
    }

    public void setIndQuestionInEditMode(int indQuestionInEditMode) {
        this.indQuestionInEditMode = indQuestionInEditMode;
    }
}
