package com.eisgroup.hrcrm.dao;

import com.eisgroup.hrcrm.model.PerformanceAppraisal;
import com.eisgroup.hrcrm.model.PerformanceAppraisalGoal;
import com.eisgroup.hrcrm.model.User;

import java.util.List;

public interface PerformanceAppraisalGoalDAO extends BaseObjectDAO<PerformanceAppraisalGoal> {
    List<PerformanceAppraisalGoal> getAllGoalsByTask(PerformanceAppraisal task);

    List<PerformanceAppraisalGoal> getAllGoalsByTaskUser(PerformanceAppraisal task, User user);
}
