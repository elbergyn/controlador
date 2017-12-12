package net.inforgyn.validation;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.primefaces.component.inputtext.InputText;

@FacesValidator("maiorZeroValidation")
public class MaiorZeroValidation implements Validator {

	@Override
	public void validate(FacesContext arg0, UIComponent component, Object obj)
			throws ValidatorException {
		if (obj != null) {
			Double valor = Double.parseDouble(obj.toString());
			component.clearInitialState();
			if (valor <= 0) {
				((InputText) component).setValue("");
				throw new ValidatorException(new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "",
						"O valor do campo deve ser maior que zero"));
			}
		}
	}

}
