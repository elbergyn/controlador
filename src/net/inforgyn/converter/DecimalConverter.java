package net.inforgyn.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import net.inforgyn.util.NumericUtil;

@FacesConverter("decimalConverter")
public class DecimalConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String valor) {
		if(!valor.isEmpty()){
			valor = valor.replace(".", "").replace(",", ".");
			return Double.parseDouble(valor)/100;
		}
		return valor;		
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object total) {
		return null != total ? NumericUtil.doubleDoisDecimais(((Double)total)*100):"";
	}

}
