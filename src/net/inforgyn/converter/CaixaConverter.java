package net.inforgyn.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import net.inforgyn.model.Caixa;
import net.inforgyn.repository.CaixaDao;
import net.inforgyn.util.faces.CDIServiceLocator;

@FacesConverter(forClass=Caixa.class)
public class CaixaConverter implements Converter {
	//@Inject 
	private CaixaDao dao;
	
	public CaixaConverter() {
		dao = CDIServiceLocator.getBean(CaixaDao.class);
	}
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String id) {
		if (!"".equals(id) && id != null) {
			Caixa caixa = (Caixa) dao.pesquisarPorId(Caixa.class,
					Long.parseLong(id));
			return caixa;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object caixa) {
		return caixa != null ? ((Caixa) caixa).getId().toString() : "";
	}

}
