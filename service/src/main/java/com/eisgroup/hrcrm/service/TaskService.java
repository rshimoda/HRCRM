package com.eisgroup.hrcrm.service;

import com.eisgroup.hrcrm.model.Task;
import org.primefaces.model.SortOrder;

import java.util.List;
import java.util.Map;

public interface TaskService {
    Task find(long id);

    void update(Task task);

    void create(Task task);

    List<Task> getResultList(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters);

    long count(Map<String, Object> filters);

    List<String> getUsedComplexities();

}
