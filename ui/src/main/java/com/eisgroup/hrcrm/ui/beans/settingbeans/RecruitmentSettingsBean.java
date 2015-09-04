package com.eisgroup.hrcrm.ui.beans.settingbeans;

import com.eisgroup.hrcrm.model.Recruitment;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;

@ManagedBean
@ViewScoped
public class RecruitmentSettingsBean extends SettingsBean implements Serializable {

    private boolean isLevelEditMode;
    private boolean isPositionEditMode;
    private boolean isComplexityEditMode;


    private String complexities;
    private String positions;
    private String levels;

    /**
     * Initializes class fields from http-parameters
     */
    @PostConstruct
    public void init() {
        super.setEditMode(false);

        complexities = taskPropertyService.getComplexitiesInString(Recruitment.class.getSimpleName());
        positions = taskPropertyService.getPositionsInString(Recruitment.class.getSimpleName());
        levels = taskPropertyService.getCandidateLevelsInString(Recruitment.class.getSimpleName());

        setEditMode(false);
        setComplexityEditMode(false);
        setLevelEditMode(false);
        setPositionEditMode(false);
    }

    public String getComplexities() {
        return complexities;
    }

    public void setComplexities(String complexities) {
        this.complexities = complexities;
    }

    public void save() {
        if (isComplexityEditMode()) {
            taskPropertyService.updateComplexities(complexities, Recruitment.class.getSimpleName());
        }

        if (isLevelEditMode()) {
            taskPropertyService.updateCandidateLevels(levels, Recruitment.class.getSimpleName());
        }

        if (isPositionEditMode()) {
            taskPropertyService.updatePositions(positions, Recruitment.class.getSimpleName());
        }
        init();
    }

    public void startEdit(String mode) {
        super.setEditMode(true);
        setComplexityEditMode(false);
        setLevelEditMode(false);

        setPositionEditMode(false);
        if ("level".equals(mode)) {
            setLevelEditMode(true);
        }

        if ("complexity".equals(mode)) {
            setComplexityEditMode(true);
        }
        if ("position".equals(mode)) {
            setPositionEditMode(true);
        }
    }

    public boolean isLevelEditMode() {
        if (!super.isEditMode() && isLevelEditMode) {
            init();
        }
        return isLevelEditMode;
    }

    public void setLevelEditMode(boolean isLevelEditMode) {
        this.isLevelEditMode = isLevelEditMode;
    }

    public boolean isPositionEditMode() {
        if (!super.isEditMode() && isPositionEditMode) {
            init();
        }
        return isPositionEditMode;
    }

    public void setPositionEditMode(boolean isPositionEditMode) {
        this.isPositionEditMode = isPositionEditMode;
    }

    public boolean isComplexityEditMode() {
        if (!super.isEditMode() && isComplexityEditMode) {
            init();
        }
        return isComplexityEditMode;
    }

    public void setComplexityEditMode(boolean isComplexityEditMode) {
        this.isComplexityEditMode = isComplexityEditMode;
    }


    public String getPositions() {
        return positions;
    }

    public void setPositions(String positions) {
        this.positions = positions;
    }

    public String getLevels() {
        return levels;
    }

    public void setLevels(String levels) {
        this.levels = levels;
    }
}