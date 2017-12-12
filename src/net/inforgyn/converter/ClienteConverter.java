package net.inforgyn.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import net.inforgyn.model.Cliente;
import net.inforgyn.repository.ClienteDao;
import net.inforgyn.util.faces.CDIServiceLocator;

@FacesConverter(forClass=Cliente.class)
public class ClienteConverter implements Converter {
	//@Inject 
	private ClienteDao dao;
	
	public ClienteConverter() {
		dao = CDIServiceLocator.getBean(ClienteDao.class);
	}

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String id) {
		if (!"".equals(id) && id != null) {
			Cliente cliente = (Cliente) dao.pesquisarPorId(Cliente.class,
					Long.parseLong(id));
			return cliente;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object cliente) {
		return cliente != null ? ((Cliente) cliente).getId().toString() : "";
	}

}
