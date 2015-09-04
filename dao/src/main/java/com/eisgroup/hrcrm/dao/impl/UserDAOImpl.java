package com.eisgroup.hrcrm.dao.impl;

import com.eisgroup.hrcrm.dao.UserDAO;
import com.eisgroup.hrcrm.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Michael Sizonenko, MYaskov
 */
@Repository(value = "userDAO")
@Transactional
public class UserDAOImpl extends BaseObjectDAOImpl<User> implements UserDAO {

    private static final Logger LOG = LoggerFactory.getLogger(UserDAOImpl.class);

    public UserDAOImpl() {
        super(User.class);
    }

    @Override
    public List<User> getAllActiveUsers() {
        LOG.info("getAllActiveUsers started");
        long tStart = System.nanoTime();

        Query query = entityManager.createQuery("select p from USERS p where (p.isDeleted = FALSE) ORDER BY p.id");
        List<User> result = query.getResultList();
        LOG.info("getAllActiveUsers finished, time: " + (System.nanoTime() - tStart) / 1000000 + " ms");

        return result;
    }

    @Override
    public List<User> getAllActiveUsersSortByName() {
        LOG.info("getAllActiveUsersByName started");
        long tStart = System.nanoTime();

        Query query = entityManager.createQuery("select p from USERS p where (p.isDeleted = FALSE) ORDER BY lower(p.firstName), lower(p.lastName)");
        List<User> result = query.getResultList();
        LOG.info("getAllActiveUsersByName finished, time: " + (System.nanoTime() - tStart) / 1000000 + " ms");

        return result;
    }

    @Override
    public User findUserByLogin(String login) {
        LOG.info("findUserByLogin started, param login=" + login);
        long tStart = System.nanoTime();

        Query query = entityManager.createQuery("select user from USERS user where (LOWER(user.login) = :login and user.isDeleted=false)");
        query.setParameter("login", login.toLowerCase());
        try {
            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            LOG.info("findUserByLogin exception: ", e);
            return null;
        } finally {
            LOG.info("findUserByLogin finished, time: " + (System.nanoTime() - tStart) / 1000000 + " ms");

        }
    }

    @Override
    public User findUserByEmail(String email) {
        LOG.info("findUserByEmail started, param email=" + email);
        long tStart = System.nanoTime();
        Query query = entityManager.createQuery("select user from USERS user where (LOWER(user.email) = :email and user.isDeleted=false)");
        query.setParameter("email", email.toLowerCase());
        try {
            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            LOG.info("findUserByEmail exception: ", e);
            return null;
        } finally {
            LOG.info("findUserByEmail finished, time: " + (System.nanoTime() - tStart) / 1000000 + " ms");

        }
    }
}
