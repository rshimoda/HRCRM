package com.eisgroup.hrcrm.dao.impl;

import com.eisgroup.hrcrm.dao.PositionDAO;
import com.eisgroup.hrcrm.model.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
public class PositionDAOImpl extends BaseObjectDAOImpl<Position> implements PositionDAO {

    private static final Logger LOG = LoggerFactory.getLogger(PositionDAOImpl.class);

    public PositionDAOImpl() {
        super(Position.class);
    }

    @Override
    public List<Position> getActivePositionsByTaskType(String taskType) {
        LOG.info("getActivePositionsByTaskType started, param: taskType=" + taskType);
        long tStart = System.nanoTime();
        Query query = entityManager.createQuery("select p from Position p where (p.isDeleted = FALSE and p.taskType = :task) ORDER BY p.name");
        query.setParameter("task", taskType);
        List<Position> result = query.getResultList();
        LOG.info("getActivePositionsByTaskType finished, time: " + (System.nanoTime() - tStart) / 1000000 + " ms");

        return result;
    }

    @Override
    public void resetToDefaultByTaskType(String taskType) {
        LOG.info("resetToDefaultByTaskType started, param: taskType=" + taskType);
        long tStart = System.nanoTime();
        Query query = entityManager.createQuery("UPDATE Position p SET p.isDeleted = FALSE WHERE (p.isDefault = TRUE and p.taskType = :task)");
        query.setParameter("task", taskType);
        query.executeUpdate();
        query = entityManager.createQuery("UPDATE Position p SET p.isDeleted = TRUE WHERE (p.isDefault = FALSE and p.taskType = :task)");
        query.setParameter("task", taskType);
        query.executeUpdate();
        LOG.info("resetToDefaultByTaskType finished, time: " + (System.nanoTime() - tStart) / 1000000 + " ms");

    }

    @Override
    public void setDeletedAllByTaskType(String taskType) {
        LOG.info("setDeletedAllByTaskType started, param: taskType=" + taskType);
        long tStart = System.nanoTime();
        Query query = entityManager.createQuery("UPDATE Position p SET p.isDeleted = TRUE WHERE p.taskType = :task");
        query.setParameter("task", taskType);
        query.executeUpdate();
        LOG.info("setDeletedAllByTaskType finished, time: " + (System.nanoTime() - tStart) / 1000000 + " ms");

    }

    @Override
    public Position getByNameByTaskType(String name, String taskType) {
        LOG.info("getByNameByTaskType started, param: name=" + name + " taskType=" + taskType);
        long tStart = System.nanoTime();
        Query query = entityManager.createQuery("select p from Position p where LOWER(p.name) = :name and p.taskType = :task");
        query.setParameter("name", name.toLowerCase());
        query.setParameter("task", taskType);
        try {
            return (Position) query.getSingleResult();
        } catch (NoResultException e) {
            LOG.info("getByName exception: ", e);
            return null;
        } finally {
            LOG.info("getByNameByTaskType finished, time: " + (System.nanoTime() - tStart) / 1000000 + " ms");

        }
    }

    @Override
    public List<String> getAllUsedCandidatePositions() {
        Query query = entityManager.createQuery("SELECT DISTINCT p.name FROM Position p WHERE p.taskType = 'Candidate'");
        return query.getResultList();
    }

    @Override
    public List<String> getAllUsedRecruitmentPosition() {
        Query query = entityManager.createQuery("SELECT DISTINCT p.name FROM Position p WHERE p.taskType = 'Recruitment'");
        return query.getResultList();
    }
}
