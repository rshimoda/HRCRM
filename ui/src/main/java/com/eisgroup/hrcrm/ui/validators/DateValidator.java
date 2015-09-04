package com.eisgroup.hrcrm.ui.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FacesValidator("dateValidator")
public class DateValidator implements Validator {

    private static final Logger LOG = LoggerFactory.getLogger(DateValidator.class);

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object object) throws ValidatorException {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date selectedDate = (Date) object;
        Date todayWithZeroTime;
        try {
            todayWithZeroTime = formatter.parse(formatter.format(new Date()));
            if (todayWithZeroTime.after(selectedDate)) {
                throw new ValidatorException(new FacesMessage());
            }

        } catch (ParseException e) {
            LOG.info(e.getMessage());
        }

    }
}
