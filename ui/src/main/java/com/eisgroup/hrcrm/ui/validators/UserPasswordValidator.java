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

/**
 * Class for validation password field for user
 *
 * @author spopov
 */

@FacesValidator("userPasswordValidator")
public class UserPasswordValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        ResourceBundle resourceBundle = PropertyResourceBundle.getBundle("messages/messages");

        String inputString = (String) value;
        if (inputString == null)
            return;

        new LatinNumberSpecialValidator().validate(context, component, value);

        Pattern p = Pattern.compile("^[\\x21-\\x7F]*$");
        Matcher m = p.matcher(inputString);
        if (!m.matches()) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "", resourceBundle.getString("Error.InvalidInputData")));
        }

        int lLimit = 4, hLimit = 8;
        if (inputString.length() < lLimit || inputString.length() > hLimit) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "", resourceBundle.getString("User.invalidPasswordLength")));
        }


    }
}
