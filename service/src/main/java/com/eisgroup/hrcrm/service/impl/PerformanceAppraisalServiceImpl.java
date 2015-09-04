package com.eisgroup.hrcrm.service.impl;

import com.eisgroup.hrcrm.dao.*;
import com.eisgroup.hrcrm.model.*;
import com.eisgroup.hrcrm.service.PerformanceAppraisalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service("performanceAppraisalService")
public class PerformanceAppraisalServiceImpl extends TaskServiceImpl implements PerformanceAppraisalService, Serializable {

    @Autowired
    PerformanceAppraisalDetailsDAO performanceAppraisalDetailsDAO;

    @Autowired
    PerformanceAppraisalGoalDAO performanceAppraisalGoalDAO;

    @Autowired
    private QuestionDAO questionDAO;

    @Autowired
    PerformanceAppraisalDAO performanceAppraisalDAO;

    @Autowired
    PerformanceAppraisalUserDAO performanceAppraisalUserDAO;

    @Override
    public PerformanceAppraisal find(long id) {
        return performanceAppraisalDAO.find(id);
    }

    @Override
    public List<Question> getAllActiveQuestions() {
        return questionDAO.getAllActiveQuestions();
    }

    @Override
    public void saveDetails(List<PerformanceAppraisalDetail> detailList) {
        if (detailList == null || detailList.isEmpty()) {
            return;
        }
        for (PerformanceAppraisalDetail detail : detailList) {
            if (detail.getAnswer() != null) {
                detail.setAnswer(detail.getAnswer().trim());
            }
            performanceAppraisalDetailsDAO.update(detail);
        }
    }

    @Override
    public void saveGoals(List<PerformanceAppraisalGoal> goalList) {
        if (goalList == null || goalList.isEmpty()) {
            return;
        }
        for (PerformanceAppraisalGoal goal : goalList) {
            if (goal.getGoalAnswer() != null) {
                goal.setGoalAnswer(goal.getGoalAnswer().trim());
            }
            performanceAppraisalGoalDAO.update(goal);
        }
    }

    @Override
    public void createGoal(PerformanceAppraisalGoal goal) {
        if (goal == null) {
            return;
        }
        performanceAppraisalGoalDAO.create(goal);
    }

    @Override
    public void createDetail(PerformanceAppraisalDetail detail) {
        if (detail == null) {
            return;
        }
        performanceAppraisalDetailsDAO.create(detail);
    }

    @Override
    public List<PerformanceAppraisalDetail> getAllDetailsByTask(PerformanceAppraisal task) {
        return performanceAppraisalDetailsDAO.getAllDetailsByTask(task);
    }

    @Override
    public List<PerformanceAppraisalDetail> getAllDetailsByTaskUser(PerformanceAppraisal task, User user) {
        return performanceAppraisalDetailsDAO.getAllDetailsByTaskUser(task, user);
    }

    @Override
    public List<PerformanceAppraisalGoal> getAllGoalsByTask(PerformanceAppraisal task) {
        return performanceAppraisalGoalDAO.getAllGoalsByTask(task);
    }

    @Override
    public List<PerformanceAppraisalGoal> getAllGoalsByTaskUser(PerformanceAppraisal task, User user) {
        return performanceAppraisalGoalDAO.getAllGoalsByTaskUser(task, user);
    }

    @Override
    public List<User> getUsersByTask(PerformanceAppraisal task) {
        return performanceAppraisalUserDAO.getAllUsersByTask(task);
    }

    @Override
    public List<User> getReviewersByTask(PerformanceAppraisal task) {
        return performanceAppraisalUserDAO.getAllReviewersByTask(task);
    }

    @Override
    public boolean isUserCanAnswer(PerformanceAppraisal task, User user) {
        return performanceAppraisalUserDAO.isUserCanAnswer(task, user);
    }

    @Override
    public void createPAUser(PerformanceAppraisalUser user) {
        performanceAppraisalUserDAO.create(user);
    }


    /**
     * Add names of questions to DB
     *
     * @param questions List of questions' names
     */
    public void updateQuestions(List<String> questions) {
        if (questions == null || questions.isEmpty()) {
            questionDAO.setDeletedAll(); //set all questions to be deleted logically
            return;
        }
        questionDAO.setDeletedAll();  //set all questions to be deleted logically
        for (String currentQuestion : questions) {
            String question = currentQuestion.trim().replaceAll("\\s+", " "); // remove all unused spaces(begin,center,end)
            if (!question.isEmpty()) {
                Question newQuestion = questionDAO.getByNameNotDeleted(question); //get (if has) question from DB comparing by name
                if (newQuestion == null) {
                    newQuestion = new Question();
                    newQuestion.setName(question);
                    newQuestion.setDeleted(false);
                    questionDAO.update(newQuestion); //add question to DB
                }
            }
        }
    }

    @Override
    public void deleteQuestion(String question) {
        Question toDeleteQuestion = new Question();
        toDeleteQuestion.setName(question);
        toDeleteQuestion.setDeleted(true);
        questionDAO.delete(toDeleteQuestion);
    }
}
