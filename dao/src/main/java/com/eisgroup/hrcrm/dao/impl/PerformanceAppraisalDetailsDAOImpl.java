package com.eisgroup.hrcrm.dao.impl;

import com.eisgroup.hrcrm.dao.PerformanceAppraisalDetailsDAO;
import com.eisgroup.hrcrm.model.PerformanceAppraisal;
import com.eisgroup.hrcrm.model.PerformanceAppraisalDetail;
import com.eisgroup.hrcrm.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

@Repository(value = "performanceAppraisalDetailsDAO")
@Transactional
public class PerformanceAppraisalDetailsDAOImpl extends BaseObjectDAOImpl<PerformanceAppraisalDetail> implements PerformanceAppraisalDetailsDAO {

    private static final Logger LOG = LoggerFactory.getLogger(PerformanceAppraisalDetailsDAOImpl.class);

    public PerformanceAppraisalDetailsDAOImpl() {
        super(PerformanceAppraisalDetail.class);
    }


    @Override
    public List<PerformanceAppraisalDetail> getAllDetailsByTask(PerformanceAppraisal task) {
        LOG.info("getAllDetailsByTask started");
        long tStart = System.nanoTime();
        Query query = entityManager.createQuery("select p from PerformanceAppraisalDetail p where p.performanceAppraisal = :task ORDER BY p.id");
        query.setParameter("task", task);
        List<PerformanceAppraisalDetail> result = query.getResultList();
        LOG.info("getAllDetailsByTask finished, time: " + (System.nanoTime() - tStart) / 1000000 + " ms");

        return result;
    }

    @Override
    public List<PerformanceAppraisalDetail> getAllDetailsByTaskUser(PerformanceAppraisal task, User user) {
        LOG.info("getAllDetailsByTaskUser started");
        long tStart = System.nanoTime();
        Query query;
        if (user == null) {
            query = entityManager.createQuery("select p from PerformanceAppraisalDetail p where (p.performanceAppraisal = :task and p.user is null ) ORDER BY p.id");
            query.setParameter("task", task);

        } else {
            query = entityManager.createQuery("select p from PerformanceAppraisalDetail p where (p.performanceAppraisal = :task and p.user = :user ) ORDER BY p.id");
            query.setParameter("task", task);
            query.setParameter("user", user);
        }

        List<PerformanceAppraisalDetail> result = query.getResultList();
        LOG.info("getAllDetailsByTaskUser finished, time: " + (System.nanoTime() - tStart) / 1000000 + " ms");

        return result;
    }
}
