package com.eisgroup.hrcrm.ui.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

@FacesValidator("passwordEqValidator")
public class PasswordEqValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component,
                         Object value) throws ValidatorException {
        ResourceBundle resourceBundle = PropertyResourceBundle.getBundle("messages/messages");
        String confirmPassword = value.toString();
        Map<String, Object> attributes = component.getAttributes();
        String password = (String) attributes.get("password");

        new UserPasswordValidator().validate(context, component, value);

        if (!password.equals(confirmPassword)) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "", resourceBundle.getString("User.inequalityPasswords")));
        }


    }
}