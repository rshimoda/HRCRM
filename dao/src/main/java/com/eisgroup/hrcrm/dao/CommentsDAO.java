package com.eisgroup.hrcrm.dao;

import com.eisgroup.hrcrm.model.Comment;
import com.eisgroup.hrcrm.model.Task;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface CommentsDAO extends BaseObjectDAO<Comment> {
    List<Comment> getAllCommentsByTask(Task task);
}
