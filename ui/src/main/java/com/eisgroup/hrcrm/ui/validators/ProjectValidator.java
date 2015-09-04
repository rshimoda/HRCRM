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

@FacesValidator("projectValidator")
public class ProjectValidator implements Validator {

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object object) throws ValidatorException {
        ResourceBundle resourceBundle = PropertyResourceBundle.getBundle("messages/messages");

        String projectsString = (String) object;
        if (projectsString == null)
            return;
        String[] projects = projectsString.replaceAll("\\p{Z}+", "").split(",");

        int counter = 0;
        for (String project : projects) {
            if (project.isEmpty()) {
                continue;
            }
            counter++;
            if (project.length() > 40) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                        resourceBundle.getString("Error.InvalidProjectLength")));
            }

            //This pattern can be changed if BA will change the list of special symbols
            //Now the list is: "-_!.?"
            Pattern p2 = Pattern.compile("^[a-zA-Z0-9_!:\\-\\.\\?]*$");
            Matcher m2 = p2.matcher(project);
            if (!m2.matches()) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                        resourceBundle.getString("Error.InvalidInputData")));
            }
        }

        if (counter > 20) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                    resourceBundle.getString("AdminTask.ParametersCountError"))); //temporary error
        }
    }
}
