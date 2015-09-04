package com.eisgroup.hrcrm.dao;

import com.eisgroup.hrcrm.model.Task;
import org.primefaces.model.SortOrder;

import java.util.List;
import java.util.Map;

public interface TaskDAO extends BaseObjectDAO<Task> {

    List<Task> getAll(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters);

    long count(Map<String, Object> filters);

    List<Task> getByQuery(String queryS);

    List<String> getUsedComplexities();
}
