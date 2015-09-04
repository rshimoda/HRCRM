package com.eisgroup.hrcrm.ui.beans.settingbeans;

import com.eisgroup.hrcrm.model.Candidate;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;

@ManagedBean
@ViewScoped
public class CandidatesSettingBean extends SettingsBean implements Serializable {


    private boolean isLevelEditMode;
    private boolean isPositionEditMode;


    private String levels;
    private String positions;


    //init, edit, save

    @PostConstruct
    public void init() {
        super.setEditMode(false);

        levels = taskPropertyService.getCandidateLevelsInString(Candidate.class.getSimpleName());
        positions = taskPropertyService.getPositionsInString(Candidate.class.getSimpleName());

        setEditMode(false);
        setLevelEditMode(false);
        setPositionEditMode(false);


    }

    public void save() {
        if (isLevelEditMode()) {
            taskPropertyService.updateCandidateLevels(levels, Candidate.class.getSimpleName());
        }

        if (isPositionEditMode()) {
            taskPropertyService.updatePositions(positions, Candidate.class.getSimpleName());
        }

        init();
    }

    public void startEdit(String mode) {
        super.setEditMode(true);
        setLevelEditMode(false);
        setPositionEditMode(false);
        if ("level".equals(mode)) {
            setLevelEditMode(true);
        }

        if ("position".equals(mode)) {
            setPositionEditMode(true);
        }
    }
    //setters and getters


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

    public String getLevels() {
        return levels;
    }

    public void setLevels(String levels) {
        this.levels = levels;
    }

    public String getPositions() {
        return positions;
    }

    public void setPositions(String positions) {
        this.positions = positions;
    }
}
