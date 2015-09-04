package com.eisgroup.hrcrm.ui.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@FacesValidator("stringIntegersValidator")
public class StringIntegersValidator implements Validator {
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object object) throws ValidatorException {
        ResourceBundle resourceBundle = PropertyResourceBundle.getBundle("messages/messages");

        String integersString = (String) object;
        if (integersString == null)
            return;
        String[] integers = integersString.replaceAll("\\p{Z}+", "").split(",");

        int counter = 0;
        for (String integer : integers) {
            if (integer.isEmpty()) {
                continue;
            }
            counter++;
            if (integer.length() > 40) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "", resourceBundle.getString("Error.InvalidProjectLength")));
            }

            Pattern p2 = Pattern.compile("^[0-9]*$");
            Matcher m2 = p2.matcher(integer);
            if (!m2.matches()) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                        resourceBundle.getString("AdminTask.IntegerError")));
            }
        }

        if (counter > 20) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                    resourceBundle.getString("AdminTask.ParametersCountError"))); //temporary error
        }
    }
}
