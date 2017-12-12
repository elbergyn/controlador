package net.inforgyn.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import net.inforgyn.model.Banco;
import net.inforgyn.repository.BancoDao;
import net.inforgyn.util.faces.CDIServiceLocator;

@FacesConverter(forClass=Banco.class)
public class BancoConverter implements Converter {
	//@Inject 
	private BancoDao dao;
	
	public BancoConverter() {
		dao = CDIServiceLocator.getBean(BancoDao.class);
	}

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String id) {
		if (!"".equals(id) && id != null) {
			Banco banco = (Banco) dao.pesquisarPorId(Banco.class,
					Long.parseLong(id));
			return banco;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object banco) {
		return banco != null ? ((Banco) banco).getId().toString() : "";
	}

}
