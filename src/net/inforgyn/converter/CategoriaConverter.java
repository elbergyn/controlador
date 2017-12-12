package net.inforgyn.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import net.inforgyn.model.CategoriaDespesa;
import net.inforgyn.repository.CategoriaDespesaDao;
import net.inforgyn.util.faces.CDIServiceLocator;

@FacesConverter(forClass=CategoriaDespesa.class)
public class CategoriaConverter implements Converter {
	//@Inject 
	private CategoriaDespesaDao dao;
	
	public CategoriaConverter() {
		dao = CDIServiceLocator.getBean(CategoriaDespesaDao.class);
	}
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String id) {
		if (!"".equals(id) && id != null) {
			CategoriaDespesa categoria = (CategoriaDespesa) dao.pesquisarPorId(CategoriaDespesa.class,
					Long.parseLong(id));
			return categoria;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object categoria) {
		return categoria != null ? ((CategoriaDespesa) categoria).getId().toString() : "";
	}

}
