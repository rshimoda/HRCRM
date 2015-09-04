package com.eisgroup.hrcrm.service;

import com.eisgroup.hrcrm.model.*;

import java.util.List;

public interface PerformanceAppraisalService {
    List<PerformanceAppraisalDetail> getAllDetailsByTask(PerformanceAppraisal task);

    List<PerformanceAppraisalDetail> getAllDetailsByTaskUser(PerformanceAppraisal task, User user);

    List<PerformanceAppraisalGoal> getAllGoalsByTask(PerformanceAppraisal task);

    List<PerformanceAppraisalGoal> getAllGoalsByTaskUser(PerformanceAppraisal task, User user);

    List<Question> getAllActiveQuestions();

    void saveDetails(List<PerformanceAppraisalDetail> detailsList);

    void saveGoals(List<PerformanceAppraisalGoal> goalsList);

    void createGoal(PerformanceAppraisalGoal goal);

    void createDetail(PerformanceAppraisalDetail detail);

    void updateQuestions(List<String> questions);

    void deleteQuestion(String question);

    List<User> getUsersByTask(PerformanceAppraisal task);

    List<User> getReviewersByTask(PerformanceAppraisal task);

    boolean isUserCanAnswer(PerformanceAppraisal task, User user);

    void createPAUser(PerformanceAppraisalUser user);


    PerformanceAppraisal find(long id);
}