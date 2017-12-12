package net.inforgyn.converter;

import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import net.inforgyn.decorator.DataMenu;
import net.inforgyn.util.DataUtil;

@FacesConverter("dataMenuConverter")
public class DateMenuConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent componente,
			String data) {
		if (!"".equals(data) && data != null) {
			Date date = DataUtil.getData(data);
			return new DataMenu(date.getTime());
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object data) {
		if (data != null) {
			return data.toString();
		}
		return "";
	}

}
