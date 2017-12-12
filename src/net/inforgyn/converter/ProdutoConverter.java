package net.inforgyn.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import net.inforgyn.model.Produto;
import net.inforgyn.repository.ProdutoDao;
import net.inforgyn.util.faces.CDIServiceLocator;

@FacesConverter(forClass=Produto.class)
public class ProdutoConverter implements Converter {
	//@Inject 
	private ProdutoDao dao;
	
	public ProdutoConverter() {
		dao = CDIServiceLocator.getBean(ProdutoDao.class);
	}

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String id) {
		try{
			if (!"".equals(id) && id != null) {
				Produto produto = (Produto) dao.produtoBasico(Long.parseLong(id));
				return produto;
			}
		}catch(NumberFormatException e){
			new ConverterException(new FacesMessage(FacesMessage.SEVERITY_WARN, "detailMessages", "Produto não localizado"));
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object produto) {
		return produto != null && ((Produto) produto).getId() != null ? ((Produto) produto).getId().toString() : "";
	}

}
