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

@FacesValidator("commentsValidator")
public class CommentsValidator implements Validator {
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object object) throws ValidatorException {
        ResourceBundle resourceBundle = PropertyResourceBundle.getBundle("messages/messages");

        String summary = (String) object;
        if (summary == null)
            return;

        if ("".equals(summary.trim())) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "", resourceBundle.getString("TaskValidation.OnlySpaces")));
        }

        if (summary.length() > 2048) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "", resourceBundle.getString("TaskValidation.CommentLength")));
        }
        Pattern p = Pattern.compile("^[\\x00-\\x7F]*$");
        Matcher m = p.matcher(summary);
        if (!m.matches()) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "", resourceBundle.getString("TaskValidation.GoalLatin")));
        }
    }
}
