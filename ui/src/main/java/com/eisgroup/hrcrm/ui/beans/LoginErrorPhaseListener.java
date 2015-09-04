package com.eisgroup.hrcrm.ui.beans;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.WebAttributes;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class LoginErrorPhaseListener implements PhaseListener {
    private static final long serialVersionUID = -1216620620302322995L;

    @Override
    public void beforePhase(final PhaseEvent arg0) {
        Exception e = (Exception) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap()
                .get(WebAttributes.AUTHENTICATION_EXCEPTION);

        if (e instanceof BadCredentialsException) {
//FacesContext
//.getCurrentInstance()
//.getExternalContext()
//.getSessionMap()
//.put(WebAttributes.AUTHENTICATION_EXCEPTION, null);
            ResourceBundle resourceBundle = PropertyResourceBundle.getBundle("messages/messages");
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                            resourceBundle.getString("Error.NoUserFound")));
        }
    }

    @Override
    public void afterPhase(final PhaseEvent arg0) {
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RENDER_RESPONSE;
    }

}
