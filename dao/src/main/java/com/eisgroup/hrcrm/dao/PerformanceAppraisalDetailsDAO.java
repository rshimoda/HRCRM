package com.eisgroup.hrcrm.dao;

import com.eisgroup.hrcrm.model.PerformanceAppraisal;
import com.eisgroup.hrcrm.model.PerformanceAppraisalDetail;
import com.eisgroup.hrcrm.model.User;

import java.util.List;

public interface PerformanceAppraisalDetailsDAO extends BaseObjectDAO<PerformanceAppraisalDetail> {
    List<PerformanceAppraisalDetail> getAllDetailsByTask(PerformanceAppraisal task);

    List<PerformanceAppraisalDetail> getAllDetailsByTaskUser(PerformanceAppraisal task, User user);
}
