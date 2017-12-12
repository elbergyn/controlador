package net.inforgyn.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import net.inforgyn.model.TipoRecebimento;
import net.inforgyn.repository.TipoRecebimentoDao;
import net.inforgyn.util.faces.CDIServiceLocator;

@FacesConverter(forClass=TipoRecebimento.class)
public class TipoRecebimentoConverter implements Converter {
	//@Inject 
	private TipoRecebimentoDao dao;
	
	public TipoRecebimentoConverter() {
		dao = CDIServiceLocator.getBean(TipoRecebimentoDao.class);
	}

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String id) {
		try{
			if (!"".equals(id) && id != null) {
				TipoRecebimento recebimento = (TipoRecebimento) dao.pesquisarPorId(TipoRecebimento.class,
					Long.parseLong(id));
				return recebimento;
			}
		}catch(NumberFormatException e){
			new ConverterException(new FacesMessage(FacesMessage.SEVERITY_WARN, "detailMessages", "TipoRecebimento não localizado"));
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object recebimento) {
		return recebimento != null && ((TipoRecebimento) recebimento).getId() != null ? ((TipoRecebimento) recebimento).getId().toString() : "";
	}

}
