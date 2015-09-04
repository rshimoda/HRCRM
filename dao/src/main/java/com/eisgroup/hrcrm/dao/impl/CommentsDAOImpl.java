package com.eisgroup.hrcrm.dao.impl;

import com.eisgroup.hrcrm.dao.CommentsDAO;
import com.eisgroup.hrcrm.model.Comment;
import com.eisgroup.hrcrm.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

@Repository(value = "commentsDAO")
@Transactional
public class CommentsDAOImpl extends BaseObjectDAOImpl<Comment> implements CommentsDAO {

    private static final Logger LOG = LoggerFactory.getLogger(CommentsDAOImpl.class);

    public CommentsDAOImpl() {
        super(Comment.class);
    }

    @Override
    public List<Comment> getAllCommentsByTask(Task task) {
        LOG.info("getAllCommentsByTask started");
        long tStart = System.nanoTime();
        Query query = entityManager.createQuery("select p from COMMENTS p where p.task =  :id ORDER BY p.id");
        query.setParameter("id", task);
        List<Comment> result = query.getResultList();
        LOG.info("getAllCommentsByTask finished, time: " + (System.nanoTime() - tStart) / 1000000 + " ms");
        return result;
    }
}
