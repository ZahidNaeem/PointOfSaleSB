package org.zahid.apps.web.pos.utils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("phoneConverter")
public class PhoneConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		String phone = (String) value;
		StringBuilder sb = new StringBuilder();
		if (phone.length() >= 3) {
			sb.append("(").append(phone.substring(0, 3)).append(") ");
		}
		if (phone.length() >= 7) {
			sb.append(phone.substring(3, 7)).append(" ");
		}
		if (phone.length() > 7) {
			sb.append(phone.substring(7));
		}
		return sb != null && sb.length() > 0 ? sb.toString() : null;
	}

}
