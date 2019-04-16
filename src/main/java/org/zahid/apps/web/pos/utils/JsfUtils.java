package org.zahid.apps.web.pos.utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class JsfUtils {
    public static void showMessage(FacesMessage.Severity severity, String msg) {
        FacesMessage fm = new FacesMessage(severity, msg, msg);
        FacesContext.getCurrentInstance().addMessage(null, fm);
    }

    public static void showMessage(FacesMessage.Severity severity, String title, String msg) {
        FacesMessage fm = new FacesMessage(severity, title, msg);
        FacesContext.getCurrentInstance().addMessage(null, fm);
    }

}
