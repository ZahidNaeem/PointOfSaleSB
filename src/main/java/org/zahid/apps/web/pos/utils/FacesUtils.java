package org.zahid.apps.web.pos.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.selectbooleancheckbox.SelectBooleanCheckbox;
import org.primefaces.component.selectonemenu.SelectOneMenu;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import java.util.Iterator;

public class FacesUtils {
    private static final Logger LOG = LogManager.getLogger(FacesUtils.class);

    public static void disableComponent(String componentName) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        UIComponent component = ctx.getViewRoot().findComponent(componentName);
        disableAll(component);
    }

    public static void disableAll(UIComponent component) {
        Iterator<UIComponent> facetsAndChildren = component.getFacetsAndChildren();
        while (facetsAndChildren.hasNext()) {
            UIComponent child = facetsAndChildren.next();
            LOG.info(child.getClass().getTypeName());
            if (child instanceof InputText) {
                ((InputText) child).setDisabled(true);
            } else if (child instanceof HtmlInputText) {
                ((HtmlInputText) child).setDisabled(true);
            } else if (child instanceof SelectOneMenu) {
                ((SelectOneMenu) child).setDisabled(true);
            } else if (child instanceof SelectBooleanCheckbox) {
                ((SelectBooleanCheckbox) child).setDisabled(true);
            } else if (child instanceof CommandButton) {
                ((CommandButton) child).setDisabled(true);
            } else {
                disableAll(child);
            }
        }
    }
}
