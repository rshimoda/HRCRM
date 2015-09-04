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

@FacesValidator("stringValuesValidator")
public class StringValuesValidator implements Validator {
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object object) throws ValidatorException {
        ResourceBundle resourceBundle = PropertyResourceBundle.getBundle("messages/messages");

        String valuesString = (String) object;
        int counter = 0;
        if (valuesString == null)
            return;
        String[] values = valuesString.replaceAll("\\p{Z}+", "").split(",");

        for (String complexity : values) {
            if (complexity.isEmpty()) {
                continue;
            }
            counter++;
            if (complexity.length() > 40) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "", resourceBundle.getString("Error.InvalidProjectLength")));
            }


            Pattern p2 = Pattern.compile("^[a-zA-Z]*$");
            Matcher m2 = p2.matcher(complexity);
            if (!m2.matches()) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                        resourceBundle.getString("AdminTask.LatinError")));
            }
        }

        if (counter > 20) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                    resourceBundle.getString("AdminTask.ParametersCountError"))); //temporary error
        }
    }
}

