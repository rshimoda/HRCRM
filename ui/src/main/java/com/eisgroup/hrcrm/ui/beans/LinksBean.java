package com.eisgroup.hrcrm.ui.beans;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

@ManagedBean
@ViewScoped
public class LinksBean implements Serializable {


    public static final String MAIN_PATH = "/ViewTasks.xhtml";
    public static final String ADMIN_PATH = "/admin/AdminApplication.xhtml";
    public static final String ADMIN = "Admin";
    public static final String MAIN = "Main";

    String path;
    String linkName;

    @PostConstruct
    public void init() {
        HttpServletRequest origRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String link = origRequest.getRequestURL().toString();
        if (link.contains("/admin/")) {
            setPath(MAIN_PATH);
            setLinkName(MAIN);
        } else {
            setPath(ADMIN_PATH);
            setLinkName(ADMIN);
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }
}
