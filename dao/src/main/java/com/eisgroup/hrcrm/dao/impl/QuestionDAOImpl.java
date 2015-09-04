package com.eisgroup.hrcrm.dao.impl;

import com.eisgroup.hrcrm.dao.QuestionDAO;
import com.eisgroup.hrcrm.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
public class QuestionDAOImpl extends BaseObjectDAOImpl<Question> implements QuestionDAO {

    private static final Logger LOG = LoggerFactory.getLogger(QuestionDAOImpl.class);

    public QuestionDAOImpl() {
        super(Question.class);
    }

    @Override
    public List<Question> getAllActiveQuestions() {
        LOG.info("getAllActiveQuestions started");
        long tStart = System.nanoTime();
        Query query = entityManager.createQuery("select q from QUESTIONS q where  q.isDeleted = FALSE ORDER BY q.id");
        List<Question> result = query.getResultList();

        LOG.info("getAllActiveQuestions finished, time: " + (System.nanoTime() - tStart) / 1000000 + " ms");

        return result;
    }

    @Override
    public void setDeletedAll() {
        LOG.info("setDeletedAll started");
        long tStart = System.nanoTime();
        Query query = entityManager.createQuery("UPDATE QUESTIONS q SET q.isDeleted = TRUE");
        query.executeUpdate();
        LOG.info("setDeletedAll finished, time: " + (System.nanoTime() - tStart) / 1000000 + " ms");

    }

    @Override
    public Question getByNameNotDeleted(String name) {
        LOG.info("getByNameNotDeleted started, param: name=" + name);
        long tStart = System.nanoTime();
        Query query = entityManager.createQuery("select q from QUESTIONS q where (LOWER(q.name) = :name and q.isDeleted = FALSE)");
        query.setParameter("name", name.toLowerCase());
        try {
            return (Question) query.getSingleResult();
        } catch (NoResultException e) {
            LOG.info("getByNameNotDeleted exception: ", e);
            return null;
        } finally {
            LOG.info("getByNameNotDeleted finished, time: " + (System.nanoTime() - tStart) / 1000000 + " ms");
        }
    }

}
