package com.eisgroup.hrcrm.ui.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

@ManagedBean(name = "loginBean")
@SessionScoped

public class LoginBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private boolean rememberMe = false;
    private boolean loggedIn = false;
    private static final Logger LOG = LoggerFactory.getLogger(LoginBean.class);

    @ManagedProperty(value = "#{authenticationManager}")
    private AuthenticationManager authManager;

//    @ManagedProperty(value = "#{humanResourceService}")
//    private HumanResourceServiceImpl humanResourceService;
//    private List<HumanResourceManager> humanResourceManagerList;


//    @PostConstruct
//    public void init() {
//        humanResourceManagerList = humanResourceService.getAllActiveUsers();
//    }

    public void checkAuthentication() throws IOException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        if (externalContext.getUserPrincipal() != null) {
            externalContext.redirect(externalContext.getRequestContextPath() + "/ViewTasks.xhtml");
        }
    }

    public String loginProject() throws IOException, ServletException {

        LOG.info("Starting to login");

        try {
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

            RequestDispatcher dispatcher = ((ServletRequest) context.getRequest())
                    .getRequestDispatcher("/j_spring_security_check");

            dispatcher.forward((ServletRequest) context.getRequest(),
                    (ServletResponse) context.getResponse());

            FacesContext.getCurrentInstance().responseComplete();
        } catch (final Exception e) {
            ResourceBundle resourceBundle = PropertyResourceBundle.getBundle("messages/messages");
            LOG.error("Error LOG in " + e);
            FacesContext.getCurrentInstance().addMessage("loginButton",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", resourceBundle.getString("Error.NoUserFound")));

        } finally {
            setUsername("");
        }
        return null;
    }

    public String logout() {
        SecurityContextHolder.clearContext();
        setLoggedIn(false);
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        session.invalidate();

        return "loggedOut";
    }


    public void setAuthManager(AuthenticationManager authManager) {
        this.authManager = authManager;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
