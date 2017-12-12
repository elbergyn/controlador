package net.inforgyn.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import net.inforgyn.model.BandeiraCartao;
import net.inforgyn.repository.BandeiraCartaoDao;
import net.inforgyn.util.faces.CDIServiceLocator;

@FacesConverter(forClass=BandeiraCartao.class)
public class BandeiraCartaoConverter implements Converter {
	//@Inject 
	private BandeiraCartaoDao dao;
	
	public BandeiraCartaoConverter() {
		dao = CDIServiceLocator.getBean(BandeiraCartaoDao.class);
	}
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String id) {
		if (!"".equals(id) && id != null) {
			return dao.pesquisarPorId(BandeiraCartao.class, Long.parseLong(id));
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object banco) {
		return banco != null ? ((BandeiraCartao)banco).getId().toString() : "";
	}
}
