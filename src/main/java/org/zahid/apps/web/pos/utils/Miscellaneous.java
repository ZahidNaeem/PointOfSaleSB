package org.zahid.apps.web.pos.utils;

import java.util.logging.Logger;

public class Miscellaneous {
	private static Logger logger = Logger.getLogger(Miscellaneous.class.getName());
	public static Exception getNestedException(Exception rootException) {
		Exception exception = rootException;
		if (exception.getCause() == null) {
			logger.info("Last Exception: " + exception);
			return exception;
		} else {
			Exception cause = (Exception) exception.getCause();
            logger.info(exception.getClass().getName() + "Exception Cause: " + cause);
			return getNestedException(cause);
		}
	}
}
