package com.eisgroup.hrcrm.ui.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.Date;
import java.util.Map;

@FacesValidator("futureDateValidator")
public class FutureDateValidator implements Validator {
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Map<String, Object> attributes = component.getAttributes();
        Date creationDate = (Date) attributes.get("date");
        Date dueDate = (Date) value;

        if (dueDate.before(creationDate)) {
            throw new ValidatorException(new FacesMessage());
        }
    }
}