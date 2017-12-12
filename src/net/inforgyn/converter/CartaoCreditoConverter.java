package net.inforgyn.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import net.inforgyn.model.CartaoCredito;
import net.inforgyn.repository.CartaoCreditoDao;
import net.inforgyn.util.faces.CDIServiceLocator;

@FacesConverter(forClass=CartaoCredito.class)
public class CartaoCreditoConverter implements Converter {
	//@Inject 
	private CartaoCreditoDao dao;
	
	public CartaoCreditoConverter() {
		dao = CDIServiceLocator.getBean(CartaoCreditoDao.class);
	}
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String id) {
		if (!"".equals(id) && id != null) {
			CartaoCredito cartao = (CartaoCredito) dao.pesquisarPorId(CartaoCredito.class, Long.parseLong(id));
			return cartao;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object cartao) {
		return cartao != null && cartao != "" && ((CartaoCredito) cartao).getId() != null ? ((CartaoCredito) cartao)
				.getId().toString() : "";
	}

}
