package com.eisgroup.hrcrm.dao.repository;


import com.eisgroup.hrcrm.dao.model.BaseObject;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 20.01.2015
 * Time: 12:19
 */
@Transactional
public interface BaseObjectDAO<T extends BaseObject> {
    void create(T object);

    void update(T object);

    T find(long id);

    List<T> getAll();

    void delete(T object);
}
