package org.zahid.apps.web.pos.utils;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.BeanValidator;

/**
 * Lenient bean validator which disable validation in certain cases
 * in order to let the user navigate to sub view without loosing the
 * data entered in input field.
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class LenientBeanValidator extends BeanValidator {
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) {
        if (doValidation(context)) {
            super.validate(context, component, value);
        }
        // simply skip it...
    }

    private boolean doValidation(FacesContext context) {
        // main page (action that saves the main entity)
        if (context.getExternalContext().getRequestParameterValuesMap().containsKey("form:save")) {
            return true;
        }

        // sub page (action that binds the entered data to an associated entity)
        if (context.getExternalContext().getRequestParameterValuesMap().containsKey("form:ok")) {
            return true;
        }

        return false;
    }
}

