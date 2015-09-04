package com.eisgroup.hrcrm.dao.impl;

import com.eisgroup.hrcrm.dao.CandidateLevelDAO;
import com.eisgroup.hrcrm.model.CandidateLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
public class CandidateLevelDAOImpl extends BaseObjectDAOImpl<CandidateLevel> implements CandidateLevelDAO {

    private static final Logger LOG = LoggerFactory.getLogger(CandidateLevelDAOImpl.class);

    public CandidateLevelDAOImpl() {
        super(CandidateLevel.class);
    }

    @Override
    public List<CandidateLevel> getActiveCandidateLevelsByTaskType(String className) {
        LOG.info("getActiveCandidateLevelsByTaskType started, param: className=" + className);
        long tStart = System.nanoTime();
        Query query = entityManager.createQuery("select p from CandidateLevel p where (p.taskType = :task and p.isDeleted = FALSE)");
        query.setParameter("task", className);
        List<CandidateLevel> result = query.getResultList();
        LOG.info("getActiveCandidateLevelsByTaskType finished, time: " + (System.nanoTime() - tStart) / 1000000 + " ms");
        return result;
    }

    @Override
    public List<CandidateLevel> getAllCandidateLevelsByTaskType(String className) {
        LOG.info("getAllCandidateLevelsByTaskType started, param: className=" + className);
        long tStart = System.nanoTime();
        Query query = entityManager.createQuery("select p from CandidateLevel p where (p.taskType = :task)");
        query.setParameter("task", className);
        List<CandidateLevel> result = query.getResultList();
        LOG.info("getAllCandidateLevelsByTaskType finished, time: " + (System.nanoTime() - tStart) / 1000000 + " ms");
        return result;
    }

    @Override
    public void resetToDefault(String className) {
        LOG.info("resetToDefault started, param: className=" + className);
        long tStart = System.nanoTime();
        Query query = entityManager.createQuery("UPDATE CandidateLevel p SET p.isDeleted = FALSE WHERE (p.taskType = :task and p.isDefault = TRUE)");
        query.setParameter("task", className);
        query.executeUpdate();
        query = entityManager.createQuery("UPDATE CandidateLevel p SET p.isDeleted = TRUE WHERE (p.taskType = :task and p.isDefault = FALSE)");
        query.setParameter("task", className);
        query.executeUpdate();
        LOG.info("resetToDefault finished, time: " + (System.nanoTime() - tStart) / 1000000 + " ms");

    }

    @Override
    public void setDeletedAll(String className) {
        LOG.info("setDeletedAll started, param: className=" + className);
        long tStart = System.nanoTime();
        Query query = entityManager.createQuery("UPDATE CandidateLevel p SET p.isDeleted = TRUE WHERE p.taskType = :task");
        query.setParameter("task", className);
        query.executeUpdate();
        LOG.info("setDeletedAll finished, time: " + (System.nanoTime() - tStart) / 1000000 + " ms");

    }

    @Override
    public CandidateLevel getByName(String name, String className) {
        LOG.info("getByName started, param: name=" + name + " className=" + className);
        long tStart = System.nanoTime();
        Query query = entityManager.createQuery("select p from CandidateLevel p where (LOWER(p.name) = :name and p.taskType = :task)");
        query.setParameter("name", name);
        query.setParameter("task", className);

        try {
            return (CandidateLevel) query.getSingleResult();

        } catch (NoResultException e) {
            LOG.info("getByName exception: ", e);
            return null;
        } finally {
            LOG.info("getByName finished, time: " + (System.nanoTime() - tStart) / 1000000 + " ms");

        }
    }

    @Override
    public List<String> getAllUsedCandidateLevel() {
        Query query = entityManager.createQuery("SELECT DISTINCT l.name FROM CandidateLevel l WHERE l.taskType = 'Candidate'");
        return query.getResultList();
    }

    @Override
    public List<String> getAllUsedRecruitmentLevel() {
        Query query = entityManager.createQuery("SELECT DISTINCT l.name FROM CandidateLevel l WHERE l.taskType = 'Recruitment'");
        return query.getResultList();
    }
}
