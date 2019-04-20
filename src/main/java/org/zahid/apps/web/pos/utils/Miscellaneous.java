package org.zahid.apps.web.pos.utils;

import java.util.HashSet;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Miscellaneous {
    private static final Logger LOG = Logger.getLogger(Miscellaneous.class.getName());

    public static Exception getNestedException(Exception rootException) {
        Exception exception = rootException;
        if (exception.getCause() == null) {
            LOG.log(Level.INFO, "Last Exception: {0}", exception);
            return exception;
        } else {
            Exception cause = (Exception) exception.getCause();
            LOG.log(Level.INFO, exception.getClass().getName() + "Exception Cause: {0}", cause);
            return getNestedException(cause);
        }
    }

    public static String getResourceMessage(String rsrcBundle, String key) throws NullPointerException, MissingResourceException, ClassCastException {
        ResourceBundle bundle = ResourceBundle.getBundle(rsrcBundle);
        return bundle.getString(key);
    }

    public static ResourceBundle getResourceBundle(String bundle) throws NullPointerException, MissingResourceException {
        return ResourceBundle.getBundle(bundle);
    }

    public static String convertDBError(Exception e) {
        final String[] resourceMessage = {null};
        String errorMessage = Miscellaneous.getNestedException(e).getMessage();
        Set<ResourceBundle> rbList = new HashSet<>();
        rbList.add(Miscellaneous.getResourceBundle("dbconstraints"));
        rbList.add(Miscellaneous.getResourceBundle("dberrors"));

//        rbList.stream().filter(f -> false == found[0]).forEach(rb -> {
        rbList.forEach(rb -> {
            rb.keySet().stream().filter(s -> errorMessage.toUpperCase().contains(s.toUpperCase())).map(s -> Miscellaneous.getResourceMessage(rb.getBaseBundleName(), s)).forEach(message -> {
                LOG.log(Level.INFO, "Message: {0}", message);
//                found[0] = true;
                resourceMessage[0] = message;
            });
        });

//        outer:
//        for (ResourceBundle rb : rbList) {
// for (String key : rb.keySet()) {
//                if (errorMessage.toUpperCase().contains(key.toUpperCase())) {
//                    String msg = Miscellaneous.getResourceMessage(rb.getBaseBundleName(), key);
//                    resourceMessage[0] = msg;
//                    break outer;
//                }
//            }
//        }

        return resourceMessage[0];
    }
}
