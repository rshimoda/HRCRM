package com.eisgroup.hrcrm.dao;

import com.eisgroup.hrcrm.model.PerformanceAppraisal;
import com.eisgroup.hrcrm.model.PerformanceAppraisalUser;
import com.eisgroup.hrcrm.model.User;

import java.util.List;

public interface PerformanceAppraisalUserDAO extends BaseObjectDAO<PerformanceAppraisalUser> {
    List<User> getAllUsersByTask(PerformanceAppraisal task);

    List<User> getAllReviewersByTask(PerformanceAppraisal task);

    boolean isUserCanAnswer(PerformanceAppraisal task, User user);
}
