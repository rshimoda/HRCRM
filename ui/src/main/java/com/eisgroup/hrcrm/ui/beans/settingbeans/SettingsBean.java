package com.eisgroup.hrcrm.ui.beans.settingbeans;

import com.eisgroup.hrcrm.service.TaskPropertyService;

import javax.faces.bean.ManagedProperty;

/**
 * Used to store general task operation in editMode
 *
 * @author VKarpenko
 */
public abstract class SettingsBean {

    @ManagedProperty(value = "#{taskPropertyService}")
    protected TaskPropertyService taskPropertyService;

    @ManagedProperty(value = "#{editModeBean}")
    protected EditModeBean editModeBean;

    /**
     * Return if task is in editMode
     *
     * @return boolean
     */
    public boolean isEditMode() {
        return editModeBean.isEditMode();
    }

    /**
     * Set edit mode
     *
     * @param isEditMode
     */
    public void setEditMode(boolean isEditMode) {
        editModeBean.setEditMode(isEditMode);
    }

    /**
     * Used to inject service
     *
     * @param taskPropertyService
     */
    public void setTaskPropertyService(TaskPropertyService taskPropertyService) {
        this.taskPropertyService = taskPropertyService;
    }

    /**
     * Used to inject editModeBean
     *
     * @param editModeBean
     */
    public void seteditModeBean(EditModeBean editModeBean) {
        this.editModeBean = editModeBean;
    }
}
