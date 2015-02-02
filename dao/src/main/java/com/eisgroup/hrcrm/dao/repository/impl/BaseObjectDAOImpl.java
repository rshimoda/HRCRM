package com.eisgroup.hrcrm.dao.repository.impl;


import com.eisgroup.hrcrm.dao.model.BaseObject;
import com.eisgroup.hrcrm.dao.repository.BaseObjectDAO;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 20.01.2015
 * Time: 12:22
 */
@Transactional
public abstract class BaseObjectDAOImpl<T extends BaseObject> implements BaseObjectDAO<T> {

    private Class<T> type;
    @PersistenceContext
    protected EntityManager entityManager;

    public BaseObjectDAOImpl(Class<T> type) {
        this.type = type;
    }

    @Override
    public void create(T object) {

        entityManager.persist(object);
    }

    @Override
    public void update(T object) {
        entityManager.merge(object);
    }

    @Override
    public List<T> getAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteria = criteriaBuilder.createQuery(type);
        criteria.from(type);
        return entityManager.createQuery(criteria).getResultList();
    }

    @Override
    public T find(long id) {
        return entityManager.find(type, id);

    }

    @Override
    public void delete(T object) {
        entityManager.remove(object);
    }
}
