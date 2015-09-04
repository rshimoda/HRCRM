package com.eisgroup.hrcrm.dao;

import com.eisgroup.hrcrm.model.Question;

import java.util.List;

public interface QuestionDAO extends BaseObjectDAO<Question> {

    List<Question> getAllActiveQuestions();

    void setDeletedAll();

    Question getByNameNotDeleted(String name);

}
