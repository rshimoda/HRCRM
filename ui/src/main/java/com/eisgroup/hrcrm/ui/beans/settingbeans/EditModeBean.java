package com.eisgroup.hrcrm.ui.beans.settingbeans;

import org.primefaces.component.tabview.TabView;
import org.primefaces.event.TabChangeEvent;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;

@ManagedBean(name = "editModeBean")
@ViewScoped
public class EditModeBean implements Serializable {
    private int tabIndexSettingsLevel = 0;
    private int tabIndexTopLevel = 0;
    TabView tabViewSettingsLevel;
    TabView tabViewTopLevel;
    private boolean isEditMode;

    /**
     * Used to handle tab clicking and remembering state of tabPanel
     *
     * @param event
     */
    public void tabClickHandler(TabChangeEvent event) {
        TabView tabView = (TabView) event.getComponent();
        int activeIndex = tabView.getChildren().indexOf(event.getTab());

        if (!isEditMode()) {
            if ("tabsView".equals(tabView.getId())) {
                tabViewTopLevel = tabView;
                tabIndexTopLevel = activeIndex;
            } else if ("tabViewTasksSettings".equals(tabView.getId())) {
                tabViewSettingsLevel = tabView;
                tabIndexSettingsLevel = activeIndex;
            }
            tabView.setActiveIndex(activeIndex);
            return;
        }

        if ("tabsView".equals(tabView.getId())) {
            tabView.setActiveIndex(tabIndexTopLevel);
        } else if ("tabViewTasksSettings".equals(tabView.getId())) {
            tabView.setActiveIndex(tabIndexSettingsLevel);
        }
    }

    /**
     * Used to get state of general editMode
     *
     * @return
     */
    public boolean isEditMode() {
        return isEditMode;
    }

    /**
     * Used to set state of general editMode
     *
     * @param isEditMode
     */
    public void setEditMode(boolean isEditMode) {
        this.isEditMode = isEditMode;
    }
}
