package net.inforgyn.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import net.inforgyn.model.Fornecedor;
import net.inforgyn.repository.FornecedorDao;
import net.inforgyn.util.faces.CDIServiceLocator;

@FacesConverter(forClass=Fornecedor.class)
public class FornecedorConverter implements Converter {
	//@Inject 
	private FornecedorDao dao;
	
	public FornecedorConverter() {
		dao = CDIServiceLocator.getBean(FornecedorDao.class);
	}

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String id) {
		if (!"".equals(id) && id != null) {
			Fornecedor fornecedor = (Fornecedor) dao.pesquisarPorId(Fornecedor.class,
					Long.parseLong(id));
			return fornecedor;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object fornecedor) {
		return fornecedor != null && ((Fornecedor) fornecedor).getId() != null ? ((Fornecedor) fornecedor).getId().toString() : "";
	}

}
