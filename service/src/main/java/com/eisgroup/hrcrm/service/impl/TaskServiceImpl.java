package com.eisgroup.hrcrm.service.impl;

import com.eisgroup.hrcrm.dao.TaskDAO;
import com.eisgroup.hrcrm.model.Task;
import com.eisgroup.hrcrm.service.TaskService;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Service("taskService")
public class TaskServiceImpl implements TaskService, Serializable {
    @Autowired
    TaskDAO taskDAO;

    @Override
    public Task find(long id) {
        return taskDAO.find(id);
    }

    @Override
    public void update(Task task) {
        taskDAO.update(task);
    }

    @Override
    public void create(Task task) {
        taskDAO.create(task);
    }

    public List<Task> getResultList(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        return this.taskDAO.getAll(first, pageSize, sortField, sortOrder, filters);
    }

    public long count(Map<String, Object> filters) {
        return this.taskDAO.count(filters);
    }

    public List<String> getUsedComplexities() {
        return taskDAO.getUsedComplexities();
    }
}
