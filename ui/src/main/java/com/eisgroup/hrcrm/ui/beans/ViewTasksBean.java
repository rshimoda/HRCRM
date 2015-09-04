package com.eisgroup.hrcrm.ui.beans;

import com.eisgroup.hrcrm.model.Priority;
import com.eisgroup.hrcrm.model.Status;
import com.eisgroup.hrcrm.model.Task;
import com.eisgroup.hrcrm.service.TaskService;
import org.primefaces.component.calendar.Calendar;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.selectcheckboxmenu.SelectCheckboxMenu;
import org.primefaces.context.RequestContext;
import org.primefaces.event.data.FilterEvent;
import org.primefaces.event.data.PageEvent;
import org.primefaces.event.data.SortEvent;
import org.primefaces.model.LazyDataModel;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.*;

@ManagedBean
@ViewScoped
public class ViewTasksBean implements Serializable {

    private Long firstRow;
    private ValueExpression sortBy;
    private String sortOrder = "DESCENDING";
    private Integer rowsPerPage = 25;
    private LazyDataModel<Task> dataModel;
    private Boolean isTasksExist = false;
    private List<String> usedComplexities;
    private Boolean showOnlyActiveTasks = true;
    private Map<String, Object> filters;
    private Map<String, Object> filterInputs;
    private ResourceBundle resourceBundle;


    @ManagedProperty(value = "#{taskService}")
    private TaskService taskService;

    public void onPageChange(PageEvent event) {
        DataTable dataTable = (DataTable) event.getSource();
        setFirstRow(dataTable.getFirst());
    }

    public void onPageSort(SortEvent event) {
        setSortBy(event.getSortColumn().getValueExpression("sortBy"));
        setSortOrder(event.isAscending() ? "ASCENDING" : "DESCENDING");
//        DataTable dataTable = (DataTable) event.getSource();
//        setFilters(dataTable.getFilters());
    }

    public void onPageFilter(FilterEvent event) {
        setFirstRow(0);
        DataTable dataTable = (DataTable) event.getSource();
        setFilters(dataTable.getFilters());
        if (getFilters().containsKey("status") && ((String[]) getFilters().get("status")).length > 0) {
            setShowOnlyActiveTasks(false);
            getFilters().remove("statusActive");
        }
        if (isShowOnlyActiveTasks()) {
            getFilters().put("statusActive", "CLOSED");
            getFilters().remove("status");
        }
        saveFilterState();
        dataTable.setFilters(getFilters());
        RequestContext.getCurrentInstance().update("messageForm");

    }

    public void showOnlyActiveTaskListener() {
        DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot()
                .findComponent("taskTableForm:taskTable");
        setFilters(dataTable.getFilters());
        getFilters().remove("status");


        if (isShowOnlyActiveTasks()) {
            getFilters().put("statusActive", "CLOSED");
            FacesContext facesContext = FacesContext.getCurrentInstance();
            SelectCheckboxMenu ddl = (SelectCheckboxMenu) facesContext.getViewRoot().findComponent("taskTableForm:taskTable:statusFilter");
            ddl.setLabel(null);

        } else {
            getFilters().remove("statusActive");
        }
        dataTable.setFilters(getFilters());

        SelectCheckboxMenu ddl = (SelectCheckboxMenu) FacesContext.getCurrentInstance().getViewRoot().findComponent("taskTableForm:taskTable:statusFilter");
        ddl.resetValue();
    }

    public void updateLabel(String inputId) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SelectCheckboxMenu ddl = (SelectCheckboxMenu) facesContext.getViewRoot().findComponent("taskTableForm:taskTable:" + inputId);

        String[] values = (String[]) ddl.getValue();
        String label;

        if (values != null && values.length == 1) {
            label = values[0];

            if ("priorityFilter".equals(inputId)) {
                label = Priority.valueOf(label).getName();
            }
            if ("statusFilter".equals(inputId)) {
                label = Status.valueOf(label).getName();
            }
            if ("taskTypeFilter".equals(inputId)) {
                label = resourceBundle.getString("com.eisgroup.hrcrm.model." + label);
            }
            ddl.setLabel(label);
        } else if (values != null && values.length > 1) {
            label = "Multiple";
        } else {
            label = null;
        }

        ddl.setLabel(label);
        Map<String, Object> sessionMap = facesContext.getExternalContext().getSessionMap();
        if (filterInputs == null) {
            filterInputs = new HashMap<>();
        }
        if (label != null) {
            filterInputs.put(inputId, ddl.getValue());
            filterInputs.put(inputId + "Label", label);
        } else {
            filterInputs.remove(inputId);
            filterInputs.remove(inputId + "Label");
        }

        sessionMap.put("filterInputs", filterInputs);

    }

    private void saveFilterState() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String, Object> sessionMap = facesContext.getExternalContext().getSessionMap();
        if (filterInputs == null) {
            filterInputs = new HashMap<>();
        }
        InputText inputText = (InputText) facesContext.getViewRoot().findComponent("taskTableForm:taskTable:idFilter");
        if (!"".equals(((String) inputText.getValue()).trim())) {
            filterInputs.put("idFilter", ((String) inputText.getValue()).trim());
        } else {
            filterInputs.remove("idFilter");
        }
        inputText = (InputText) facesContext.getViewRoot().findComponent("taskTableForm:taskTable:summaryFilter");
        if (!"".equals(((String) inputText.getValue()).trim())) {
            filterInputs.put("summaryFilter", ((String) inputText.getValue()).trim());
        } else {
            filterInputs.remove("summaryFilter");
        }
        Calendar calendar = (Calendar) facesContext.getViewRoot().findComponent("taskTableForm:taskTable:creationDateFilter");
        if (calendar.getValue() != null) {
            filterInputs.put("creationDateFilter", calendar.getValue());
        } else {
            filterInputs.remove("creationDateFilter");
        }
        calendar = (Calendar) facesContext.getViewRoot().findComponent("taskTableForm:taskTable:dueDateFilter");
        if (calendar.getValue() != null) {
            filterInputs.put("dueDateFilter", calendar.getValue());
        } else {
            filterInputs.remove("dueDateFilter");
        }
        sessionMap.put("filterInputs", filterInputs);
    }

    /**
     * Used for inject service
     *
     * @param taskService Spring service
     */
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Find all dataModel registered in system
     *
     * @return List of {@link Task}
     */
    public LazyDataModel<Task> getDataModel() {
        onPageRefresh();
        return dataModel;
    }

    public void onPageRefresh() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        DataTable dataTable = (DataTable) facesContext.getViewRoot().findComponent("taskTableForm:taskTable");
        if (getSortBy() == null) {
            ELContext elContext = facesContext.getELContext();
            ExpressionFactory elFactory = facesContext.getApplication().getExpressionFactory();
            setSortBy(elFactory.createValueExpression(elContext, "#{task.id}", Long.class));
        }
        dataTable.setValueExpression("sortBy", getSortBy());

        if (dataModel.getPageSize() != 0) {
            setRowsPerPage(dataModel.getPageSize());
        }
        Map<String, Object> dataTableFilters = dataTable.getFilters();
        if (showOnlyActiveTasks) {
            dataTableFilters.put("statusActive", "CLOSED");
        } else {
            dataTableFilters.remove("statusActive");
        }
        dataTable.setFilters(dataTableFilters);
//        if (dataModel.getWrappedData()!=null) System.out.println(((List) dataModel.getWrappedData()).size());
    }

    private void loadFilterState() {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String, Object> sessionMap = facesContext.getExternalContext().getSessionMap();
        filterInputs = (Map) sessionMap.get("filterInputs");
        if (filterInputs != null) {
            InputText inputText = (InputText) facesContext.getViewRoot().findComponent("taskTableForm:taskTable:idFilter");
            inputText.setValue(filterInputs.get("idFilter"));
            inputText = (InputText) facesContext.getViewRoot().findComponent("taskTableForm:taskTable:summaryFilter");
            inputText.setValue(filterInputs.get("summaryFilter"));
            SelectCheckboxMenu ddl = (SelectCheckboxMenu) facesContext.getViewRoot().findComponent("taskTableForm:taskTable:statusFilter");
            ddl.setValue(filterInputs.get("statusFilter"));
            ddl.setLabel((String) filterInputs.get("statusFilterLabel"));
            ddl = (SelectCheckboxMenu) facesContext.getViewRoot().findComponent("taskTableForm:taskTable:taskTypeFilter");
            ddl.setValue(filterInputs.get("taskTypeFilter"));
            ddl.setLabel((String) filterInputs.get("taskTypeFilterLabel"));
            ddl = (SelectCheckboxMenu) facesContext.getViewRoot().findComponent("taskTableForm:taskTable:priorityFilter");
            ddl.setValue(filterInputs.get("priorityFilter"));
            ddl.setLabel((String) filterInputs.get("priorityFilterLabel"));
            ddl = (SelectCheckboxMenu) facesContext.getViewRoot().findComponent("taskTableForm:taskTable:complexityFilter");
            ddl.setValue(filterInputs.get("complexityFilter"));
            ddl.setLabel((String) filterInputs.get("complexityFilterLabel"));

            Calendar calendar = (Calendar) facesContext.getViewRoot().findComponent("taskTableForm:taskTable:creationDateFilter");
            calendar.setValue(filterInputs.get("creationDateFilter"));
            calendar = (Calendar) facesContext.getViewRoot().findComponent("taskTableForm:taskTable:dueDateFilter");
            calendar.setValue(filterInputs.get("dueDateFilter"));
        }
        DataTable dataTable = (DataTable) facesContext.getViewRoot().findComponent("taskTableForm:taskTable");
        Map<String, Object> dataTableFilters = dataTable.getFilters();
        dataTableFilters.putAll(this.filters);
        dataTable.setFilters(dataTableFilters);
        onPageRefresh();
    }

    /**
     * Set list of task which will be shown in view
     *
     * @param dataModel
     */
    public void setDataModel(LazyDataModel<Task> dataModel) {
        this.dataModel = dataModel;
    }

    /**
     * Init method to create all needed environment
     */
    @PostConstruct
    public void init() {
        setDataModel(new LazyTaskDataModel(taskService));
        setUsedComplexities(taskService.getUsedComplexities());
        resourceBundle = PropertyResourceBundle.getBundle("messages/messages");
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

        if (sessionMap.containsKey("filteredTaskType")) {
            sessionMap.remove("firstRow");
            sessionMap.remove("sortBy");
            sessionMap.remove("sortOrder");
            sessionMap.remove("rowsPerPage");
            sessionMap.remove("showOnlyActiveTasks");
            Object[] taskTypeArray = new String[1];
            taskTypeArray[0] = sessionMap.get("filteredTaskType");
            filters = new HashMap<>();
            filters.put("TASK_TYPE", taskTypeArray);
            sessionMap.put("filters", filters);

            SelectCheckboxMenu ddl = (SelectCheckboxMenu) FacesContext.getCurrentInstance().getViewRoot().findComponent("taskTableForm:taskTable:taskTypeFilter");
            ddl.setValue(taskTypeArray);
            String label = resourceBundle.getString("com.eisgroup.hrcrm.model." + taskTypeArray[0]);
            ddl.setLabel(label);

            if (filterInputs == null) {
                filterInputs = new HashMap<>();
            }
            filterInputs.put("taskTypeFilter", taskTypeArray);
            filterInputs.put("taskTypeFilterLabel", label);
            sessionMap.put("filterInputs", filterInputs);
            sessionMap.remove("filteredTaskType");
        }
        firstRow = sessionMap.get("firstRow") == null ? 0 : (Long) sessionMap.get("firstRow");
        sortBy = (ValueExpression) sessionMap.get("sortBy");
        sortOrder = sessionMap.get("sortOrder") == null ? "DESCENDING" : (String) sessionMap.get("sortOrder");
        rowsPerPage = sessionMap.get("rowsPerPage") == null ? 25 : (Integer) sessionMap.get("rowsPerPage");
        showOnlyActiveTasks = sessionMap.get("showOnlyActiveTasks") == null ? true : (Boolean) sessionMap.get("showOnlyActiveTasks");
        filters = sessionMap.get("filters") == null ? new HashMap<>() : (Map) sessionMap.get("filters");
        loadFilterState();
    }

    /**
     * Check for empty statement of created task list
     *
     * @return true if empty, false if dataModel exist
     */

    public boolean isShowOnlyActiveTasks() {
        return showOnlyActiveTasks;
    }

    public void setShowOnlyActiveTasks(boolean showOnlyActiveTasks) {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        sessionMap.put("showOnlyActiveTasks", showOnlyActiveTasks);
        this.showOnlyActiveTasks = showOnlyActiveTasks;
    }

    public boolean isTasksExist() {
        if (!isTasksExist) {
            setIsTasksExist(dataModel.getRowCount() > 0 || taskService.count(null) > 0);
        }
        return isTasksExist;
    }

    public long getFirstRow() {
        return firstRow;
    }

    public void setFirstRow(long firstRow) {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        sessionMap.put("firstRow", firstRow);
        this.firstRow = firstRow;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        sessionMap.put("sortOrder", sortOrder);
        this.sortOrder = sortOrder;
    }

    public ValueExpression getSortBy() {
        return sortBy;
    }

    public void setSortBy(ValueExpression sortBy) {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        sessionMap.put("sortBy", sortBy);
        this.sortBy = sortBy;
    }

    public int getRowsPerPage() {
        return rowsPerPage;
    }

    public void setRowsPerPage(int rowsPerPage) {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        sessionMap.put("rowsPerPage", rowsPerPage);
        this.rowsPerPage = rowsPerPage;
    }

    public List<String> getUsedComplexities() {
        return usedComplexities;
    }

    public void setUsedComplexities(List<String> usedComplexities) {
        this.usedComplexities = usedComplexities;
    }

    public void setIsTasksExist(boolean isTasksExist) {
        this.isTasksExist = isTasksExist;
    }

    public Map<String, Object> getFilters() {
        return filters;
    }

    public void setFilters(Map<String, Object> filters) {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        sessionMap.put("filters", filters);
        this.filters = filters;
    }

    public boolean getIsTableFiltered() {
        return filterInputs != null && !filterInputs.isEmpty();
    }

    public boolean isDateValid(String fieldId) {
        Calendar calendar = (Calendar) FacesContext.getCurrentInstance().getViewRoot().findComponent("taskTableForm:taskTable:" + fieldId);
        return !calendar.isConversionFailed();
    }
}