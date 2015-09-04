package com.eisgroup.hrcrm.ui.beans;

import com.eisgroup.hrcrm.model.Task;
import com.eisgroup.hrcrm.service.TaskService;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class LazyTaskDataModel extends LazyDataModel<Task> {

    private static final Logger LOG = LoggerFactory.getLogger(LazyTaskDataModel.class);

    private TaskService taskService;

    public LazyTaskDataModel(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public List<Task> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        LOG.info("", filters);
        List<Task> result = taskService.getResultList(first, pageSize, sortField, sortOrder, filters);
        if (getRowCount() == 0 || !filters.isEmpty()) {
            setRowCount((int) taskService.count(filters));
        }
        super.setWrappedData(result);
        return result;
    }

    @Override
    public Task getRowData(String rowKey) {
        return null;
    }

    @Override
    public Task getRowKey(Task object) {
        return null;
    }

}
