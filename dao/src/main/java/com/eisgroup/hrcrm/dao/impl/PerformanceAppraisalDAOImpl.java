package com.eisgroup.hrcrm.dao.impl;

import com.eisgroup.hrcrm.dao.PerformanceAppraisalDAO;
import com.eisgroup.hrcrm.model.PerformanceAppraisal;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository(value = "performanceAppraisalDAO")
@Transactional
public class PerformanceAppraisalDAOImpl extends BaseObjectDAOImpl<PerformanceAppraisal> implements PerformanceAppraisalDAO {
    public PerformanceAppraisalDAOImpl() {
        super(PerformanceAppraisal.class);
    }
}
