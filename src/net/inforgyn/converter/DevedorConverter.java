package net.inforgyn.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import net.inforgyn.model.Devedor;
import net.inforgyn.repository.DevedorDao;
import net.inforgyn.util.faces.CDIServiceLocator;

@FacesConverter(forClass=Devedor.class)
public class DevedorConverter implements Converter {
	//@Inject 
	private DevedorDao dao;
	
	public DevedorConverter() {
		dao = CDIServiceLocator.getBean(DevedorDao.class);
	}
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String id) {
		if(!"".equals(id) && id != null){		
			Devedor devedor = (Devedor) dao.pesquisarPorId(Devedor.class, Long.parseLong(id));
			return devedor;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object devedor) {
		return devedor != null ? ((Devedor)devedor).getId().toString() : "";
	}

}
