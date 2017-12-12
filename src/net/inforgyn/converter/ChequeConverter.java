package net.inforgyn.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import net.inforgyn.model.Cheque;
import net.inforgyn.repository.ChequeDao;
import net.inforgyn.util.faces.CDIServiceLocator;

@FacesConverter(forClass=Cheque.class)
public class ChequeConverter implements Converter {
	//@Inject 
	private ChequeDao dao;
	
	public ChequeConverter() {
		dao = CDIServiceLocator.getBean(ChequeDao.class);
	}
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String id) {
		if (!"".equals(id) && id != null) {
			return dao.pesquisarPorId(Cheque.class, Long.parseLong(id));
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object cheque) {
		return cheque != null && cheque != "" ? ((Cheque) cheque).getId().toString() : "";
	}

}
