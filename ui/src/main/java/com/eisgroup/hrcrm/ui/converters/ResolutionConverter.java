package com.eisgroup.hrcrm.ui.converters;

import com.eisgroup.hrcrm.model.Resolution;
import com.eisgroup.hrcrm.service.TaskPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@Component
@FacesConverter("resolutionConverter")
public class ResolutionConverter implements Converter {

    @Autowired
    private TaskPropertyService taskPropertyService;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && value.trim().length() > 0) {
            try {

                return taskPropertyService.getResolutionById(Long.parseLong(value));
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid Resolution."));
            }
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
        if (object != null) {
            return String.valueOf(((Resolution) object).getId());
        } else {
            return null;
        }
    }
}
