package net.inforgyn.validation;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("validarCampoBranco")
public class CampoBrancoValidation implements Validator{

	@Override
	public void validate(FacesContext arg0, UIComponent component, Object value)
			throws ValidatorException {
		String valor = value.toString();
		if(valor.length() > 0 && valor.trim().isEmpty()){
			String label = (String) component.getAttributes().get("label");
			FacesMessage msg = new FacesMessage("Campo em branco",
					"O campo \""+label+"\" não deve conter apenas espaços");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
	}

}
