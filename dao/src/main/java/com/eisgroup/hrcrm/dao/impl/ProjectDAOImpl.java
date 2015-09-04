package com.eisgroup.hrcrm.dao.impl;

import com.eisgroup.hrcrm.dao.ProjectDAO;
import com.eisgroup.hrcrm.model.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
public class ProjectDAOImpl extends BaseObjectDAOImpl<Project> implements ProjectDAO {

    private static final Logger LOG = LoggerFactory.getLogger(ProjectDAOImpl.class);

    public ProjectDAOImpl() {
        super(Project.class);
    }

/*    @Override
    public List<Project> getActiveProjectsByTaskType(String className) {
        LOG.info("getActiveProjectsByTaskType started, param: className=" + className);
        long tStart = System.nanoTime();
        Query query = entityManager.createQuery("select p from Project p WHERE (p.taskType = :task AND p.isDeleted = FALSE) ORDER BY p.name");
        query.setParameter("task", className);
        List<Project> result = query.getResultList();
        LOG.info("getActiveProjectsByTaskType finished, time: " + (System.nanoTime() - tStart) / 1000000 + " ms");

        return result;
    }*/

/*    @Override
    public List<Project> getProjectsByTaskType(String className) {
        LOG.info("getProjectsByTaskType started, param: className=" + className);
        long tStart = System.nanoTime();
        Query query = entityManager.createQuery("select p from Project p WHERE p.taskType = :task ORDER BY p.name");
        query.setParameter("task", className);
        List<Project> result = query.getResultList();
        LOG.info("getProjectsByTaskType finished, time: " + (System.nanoTime() - tStart) / 1000000 + " ms");
        return result;
    }*/

    @Override
    public List<Project> getAllActiveProjects() {
        LOG.info("getAllActiveProjects started");
        long tStart = System.nanoTime();
        Query query = entityManager.createQuery("select p from Project p WHERE p.isDeleted = FALSE");
        List<Project> result = query.getResultList();
        LOG.info("getAllActiveProjects finished, time: " + (System.nanoTime() - tStart) / 1000000 + " ms");
        return result;
    }

    @Override
    public void resetToDefault() {
        LOG.info("resetToDefault started");
        long tStart = System.nanoTime();
        Query query = entityManager.createQuery("UPDATE Project p SET p.isDeleted = FALSE WHERE (p.isDefault = TRUE)");
        query.executeUpdate();
        query = entityManager.createQuery("UPDATE Project p SET p.isDeleted = TRUE WHERE (p.isDefault = FALSE)");
        query.executeUpdate();
        LOG.info("resetToDefault finished, time: " + (System.nanoTime() - tStart) / 1000000 + " ms");

    }

    @Override
    public void setDeletedAll() {
        LOG.info("setDeletedAll started");
        long tStart = System.nanoTime();
        Query query = entityManager.createQuery("UPDATE Project p SET p.isDeleted = TRUE");
        query.executeUpdate();
        LOG.info("setDeletedAll finished, time: " + (System.nanoTime() - tStart) / 1000000 + " ms");

    }

    @Override
    public Project getByName(String name) {
        LOG.info("getByName started, param: name=" + name);
        long tStart = System.nanoTime();
        Query query = entityManager.createQuery("select p from Project p where (LOWER(p.name) = :name)");
        query.setParameter("name", name.toLowerCase());
        try {
            return (Project) query.getSingleResult();
        } catch (NoResultException e) {
            LOG.info("getByName exception: ", e);
            return null;
        } finally {
            LOG.info("getByName finished, time: " + (System.nanoTime() - tStart) / 1000000 + " ms");
        }

    }
}
