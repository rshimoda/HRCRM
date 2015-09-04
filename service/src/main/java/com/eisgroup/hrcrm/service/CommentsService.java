package com.eisgroup.hrcrm.service;

import com.eisgroup.hrcrm.model.*;

import java.util.List;

public interface CommentsService {
    void create(Comment comment);

    void update(Comment comment);

    void delete(Comment comment);

    List<Comment> getAllCommentsByTask(Task id);
}