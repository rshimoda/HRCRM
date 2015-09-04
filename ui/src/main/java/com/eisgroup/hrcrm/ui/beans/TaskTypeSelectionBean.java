package com.eisgroup.hrcrm.ui.beans;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.*;

@ManagedBean
@ViewScoped
public class TaskTypeSelectionBean implements Serializable {
    private String taskType;
    private List<SelectItem> taskTypeSelectOptions;

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }


    @PostConstruct
    public void init() {
        taskTypeSelectOptions = new ArrayList<>();
        ResourceBundle resourceBundle = PropertyResourceBundle.getBundle("messages/messages");
        Enumeration<String> keys = resourceBundle.getKeys();

        String bundlePrefix = "com.eisgroup.hrcrm.model";
        for (Enumeration<String> e = keys; keys.hasMoreElements(); ) {
            String key = e.nextElement();
            if (key.startsWith(bundlePrefix)) {
                taskTypeSelectOptions.add(new SelectItem(key, resourceBundle.getString(key)));

            }
        }
        Collections.sort(taskTypeSelectOptions, new Comparator<SelectItem>() {
            @Override
            public int compare(SelectItem o1, SelectItem o2) {
                return ((String) o1.getValue()).compareTo((String) o2.getValue());
            }
        });
    }

    public List<SelectItem> getTaskTypeSelectOptions() {
        return taskTypeSelectOptions;
    }

}
