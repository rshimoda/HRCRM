package com.eisgroup.hrcrm.ui.converters;

import com.eisgroup.hrcrm.model.User;
import com.eisgroup.hrcrm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@Component
@FacesConverter("userConverter")
public class UserConverter implements Converter {

    @Autowired
    private UserService userService;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (!"Unassigned".equals(value) && value != null && value.trim().length() > 0) {
            try {
                return userService.find(Long.parseLong(value));
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid User."));
            }
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object object) {
        if (object != null) {
            return String.valueOf(((User) object).getId());
        } else {
            return null;
        }
    }
}
