package com.eisgroup.hrcrm.dao.impl;

import com.eisgroup.hrcrm.dao.PerformanceAppraisalGoalDAO;
import com.eisgroup.hrcrm.model.PerformanceAppraisal;
import com.eisgroup.hrcrm.model.PerformanceAppraisalGoal;
import com.eisgroup.hrcrm.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
public class PerformanceAppraisalGoalDAOImpl extends BaseObjectDAOImpl<PerformanceAppraisalGoal> implements PerformanceAppraisalGoalDAO {

    private static final Logger LOG = LoggerFactory.getLogger(PerformanceAppraisalGoal.class);

    public PerformanceAppraisalGoalDAOImpl() {
        super(PerformanceAppraisalGoal.class);
    }

    @Override
    public List<PerformanceAppraisalGoal> getAllGoalsByTask(PerformanceAppraisal task) {
        LOG.info("getAllGoalsByTask started");
        long tStart = System.nanoTime();
        Query query = entityManager.createQuery("select p from PerformanceAppraisalGoal p where p.performanceAppraisal = :task ORDER BY p.id");
        query.setParameter("task", task);
        List<PerformanceAppraisalGoal> result = query.getResultList();
        LOG.info("getAllGoalsByTask finished, time: " + (System.nanoTime() - tStart) / 1000000 + " ms");

        return result;
    }

    @Override
    public List<PerformanceAppraisalGoal> getAllGoalsByTaskUser(PerformanceAppraisal task, User user) {
        LOG.info("getAllGoalsByTaskUser started");
        long tStart = System.nanoTime();

        Query query;
        if (user == null) {
            query = entityManager.createQuery("select p from PerformanceAppraisalGoal p where (p.performanceAppraisal = :task and p.user is null ) ORDER BY p.id");
            query.setParameter("task", task);

        } else {
            query = entityManager.createQuery("select p from PerformanceAppraisalGoal p where (p.performanceAppraisal = :task and p.user = :user) ORDER BY p.id");
            query.setParameter("task", task);
            query.setParameter("user", user);
        }

        List<PerformanceAppraisalGoal> result = query.getResultList();
        LOG.info("getAllGoalsByTaskUser finished, time: " + (System.nanoTime() - tStart) / 1000000 + " ms");

        return result;
    }
}
