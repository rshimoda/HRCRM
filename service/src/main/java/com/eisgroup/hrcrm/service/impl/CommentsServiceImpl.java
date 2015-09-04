package com.eisgroup.hrcrm.service.impl;

import com.eisgroup.hrcrm.dao.CommentsDAO;
import com.eisgroup.hrcrm.model.*;
import com.eisgroup.hrcrm.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service("commentsService")
public class CommentsServiceImpl implements CommentsService, Serializable {

    @Autowired
    CommentsDAO commentsDAO;

    @Override
    public void create(Comment comment) {
        commentsDAO.create(comment);
    }

    @Override
    public void update(Comment comment) {
        commentsDAO.update(comment);
    }

    @Override
    public void delete(Comment comment) {
        commentsDAO.delete(comment);
    }

    @Override
    public List<Comment> getAllCommentsByTask(Task id) {
        return commentsDAO.getAllCommentsByTask(id);
    }
}
