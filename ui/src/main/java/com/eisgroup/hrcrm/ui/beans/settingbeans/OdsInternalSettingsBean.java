package com.eisgroup.hrcrm.ui.beans.settingbeans;

import com.eisgroup.hrcrm.model.ODSInternal;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;

@ManagedBean
@ViewScoped
public class OdsInternalSettingsBean extends SettingsBean implements Serializable {

    private boolean isComplexityEditMode;
    private String complexities;

    @PostConstruct
    public void init() {
        super.setEditMode(false);
        setComplexityEditMode(false);
        complexities = taskPropertyService.getComplexitiesInString(ODSInternal.class.getSimpleName());
    }

    public String getComplexities() {
        return complexities;
    }

    public void setComplexities(String complexities) {
        this.complexities = complexities;
    }

    public void save() {
        if (isComplexityEditMode()) {
            taskPropertyService.updateComplexities(complexities, ODSInternal.class.getSimpleName());
        }
        init();
    }

    public boolean isComplexityEditMode() {
        if (!super.isEditMode() && isComplexityEditMode) {
            init();
        }
        return isComplexityEditMode;
    }

    public void startEdit(String mode) {
        super.setEditMode(true);
        if ("complexity".equals(mode)) {
            setComplexityEditMode(true);
        }
    }

    public void setComplexityEditMode(boolean isComplexityEditMode) {
        this.isComplexityEditMode = isComplexityEditMode;
    }
}