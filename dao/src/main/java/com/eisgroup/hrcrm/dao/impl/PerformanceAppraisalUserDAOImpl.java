package com.eisgroup.hrcrm.dao.impl;

import com.eisgroup.hrcrm.dao.PerformanceAppraisalUserDAO;
import com.eisgroup.hrcrm.model.PerformanceAppraisal;
import com.eisgroup.hrcrm.model.PerformanceAppraisalUser;
import com.eisgroup.hrcrm.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.Collections;
import java.util.List;

@Repository

@Transactional
public class PerformanceAppraisalUserDAOImpl extends BaseObjectDAOImpl<PerformanceAppraisalUser> implements PerformanceAppraisalUserDAO {

    private static final Logger LOG = LoggerFactory.getLogger(PerformanceAppraisalUser.class);

    public PerformanceAppraisalUserDAOImpl() {
        super(PerformanceAppraisalUser.class);
    }

    @Override
    public List<User> getAllUsersByTask(PerformanceAppraisal task) {
        LOG.info("getAllUsersByTask started");
        long tStart = System.nanoTime();
        if (task == null) {
            return Collections.emptyList();
        }
        Query query = entityManager.createQuery("select p.appraisalUser from PerformanceAppraisalUser p where p.performanceAppraisal = :task ORDER BY p.id");
        query.setParameter("task", task);
        List<User> result = query.getResultList();
        LOG.info("getAllUsersByTask finished, time: " + (System.nanoTime() - tStart) / 1000000 + " ms");

        return result;
    }

    @Override
    public List<User> getAllReviewersByTask(PerformanceAppraisal task) {
        LOG.info("getAllReviewersByTask started");
        long tStart = System.nanoTime();
        if (task == null) {
            return Collections.emptyList();
        }
        Query query = entityManager.createQuery("select p.appraisalUser from PerformanceAppraisalUser p where (p.performanceAppraisal = :task and p.isReviewer=true ) ORDER BY p.id");
        query.setParameter("task", task);
        List<User> result = query.getResultList();
        LOG.info("getAllReviewersByTask finished, time: " + (System.nanoTime() - tStart) / 1000000 + " ms");

        return result;
    }

    @Override
    public boolean isUserCanAnswer(PerformanceAppraisal task, User user) {
        LOG.info("isUserCanAnswer started");
        long tStart = System.nanoTime();
        if (task == null || user == null) {
            return false;
        }

        Query query = entityManager.createQuery("select p.appraisalUser from PerformanceAppraisalUser p where (p.performanceAppraisal = :task and p.appraisalUser=:user )");
        query.setParameter("task", task);
        query.setParameter("user", user);
        Object result = null;
        try {
            result = query.getSingleResult();
        } catch (NoResultException e) {
            LOG.info("isUserCanAnswer no user found");
            return false;
        } finally {
            LOG.info("isUserCanAnswer finished, time: " + (System.nanoTime() - tStart) / 1000000 + " ms");
        }
        return result != null;

    }

}
