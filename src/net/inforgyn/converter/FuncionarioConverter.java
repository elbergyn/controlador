package net.inforgyn.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import net.inforgyn.model.Funcionario;
import net.inforgyn.repository.FuncionarioDao;
import net.inforgyn.util.faces.CDIServiceLocator;

@FacesConverter(forClass=Funcionario.class)
public class FuncionarioConverter implements Converter {
	//@Inject 
	private FuncionarioDao dao;
	
	public FuncionarioConverter() {
		dao = CDIServiceLocator.getBean(FuncionarioDao.class);
	}

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String id) {
		if (!"".equals(id) && id != null) {
			Funcionario funcionario = (Funcionario) dao.pesquisarPorId(Funcionario.class,
					Long.parseLong(id));
			return funcionario;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object funcionario) {
		return funcionario != null ? ((Funcionario) funcionario).getId().toString() : "";
	}

}
