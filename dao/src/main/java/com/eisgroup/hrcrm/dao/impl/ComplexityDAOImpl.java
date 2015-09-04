package com.eisgroup.hrcrm.dao.impl;

import com.eisgroup.hrcrm.dao.ComplexityDAO;
import com.eisgroup.hrcrm.model.Complexity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
public class ComplexityDAOImpl extends BaseObjectDAOImpl<Complexity> implements ComplexityDAO {

    private static final Logger LOG = LoggerFactory.getLogger(ComplexityDAOImpl.class);

    public ComplexityDAOImpl() {
        super(Complexity.class);
    }

    @Override
    public List<Complexity> getActiveComplexitiesByTaskType(String className) {
        LOG.info("getActiveComplexitiesByTaskType started, param: className=" + className);
        long tStart = System.nanoTime();
        Query query = entityManager.createQuery("select p from Complexity p where (p.taskType = :task and p.isDeleted = FALSE)");
        query.setParameter("task", className);
        List<Complexity> result = query.getResultList();

        LOG.info("getActiveComplexitiesByTaskType finished, time: " + (System.nanoTime() - tStart) / 1000000 + " ms");

        return result;
    }

    @Override
    public List<Complexity> getAllComplexitiesByTaskType(String className) {
        LOG.info("getAllComplexitiesByTaskType started, param: className=" + className);
        long tStart = System.nanoTime();
        Query query = entityManager.createQuery("select p from Complexity p where (p.taskType = :task)");
        query.setParameter("task", className);
        List<Complexity> result = query.getResultList();

        LOG.info("getAllComplexitiesByTaskType finished, time: " + (System.nanoTime() - tStart) / 1000000 + " ms");

        return result;
    }

    @Override
    public void resetToDefault(String className) {
        LOG.info("resetToDefault started, param: className=" + className);
        long tStart = System.nanoTime();
        Query query = entityManager.createQuery("UPDATE Complexity p SET p.isDeleted = FALSE WHERE (p.taskType = :task and p.isDefault = TRUE)");
        query.setParameter("task", className);
        query.executeUpdate();
        query = entityManager.createQuery("UPDATE Complexity p SET p.isDeleted = TRUE WHERE (p.taskType = :task and p.isDefault = FALSE)");
        query.setParameter("task", className);
        query.executeUpdate();
        LOG.info("resetToDefault finished, time: " + (System.nanoTime() - tStart) / 1000000 + " ms");

    }

    @Override
    public void setDeletedAll(String className) {
        LOG.info("setDeletedAll started, param: className=" + className);
        long tStart = System.nanoTime();
        Query query = entityManager.createQuery("UPDATE Complexity p SET p.isDeleted = TRUE WHERE p.taskType = :task");
        query.setParameter("task", className);
        query.executeUpdate();
        LOG.info("setDeletedAll finished, time: " + (System.nanoTime() - tStart) / 1000000 + " ms");

    }

    @Override
    public Complexity getByName(String name, String className) {
        LOG.info("getByName started, param: name=" + name + " className=" + className);
        long tStart = System.nanoTime();
        Query query = entityManager.createQuery("select p from Complexity p where (LOWER(p.name) = :name and p.taskType = :task)");
        query.setParameter("name", name.toLowerCase());
        query.setParameter("task", className);

        try {
            return (Complexity) query.getSingleResult();
        } catch (NoResultException e) {
            LOG.info("getByName exception: ", e);
            return null;
        } finally {
            LOG.info("getByName finished, time: " + (System.nanoTime() - tStart) / 1000000 + " ms");

        }
    }
}
