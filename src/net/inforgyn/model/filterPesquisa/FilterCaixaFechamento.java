package net.inforgyn.model.filterPesquisa;

import java.util.Date;

public class FilterCaixaFechamento {
	private Date inicio;
	private Date fim;
	
	public FilterCaixaFechamento() {
		super();
	}
	
	public Date getInicio() {
		return inicio;
	}
	public Date getFim() {
		return fim;
	}
	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}
	public void setFim(Date fim) {
		this.fim = fim;
	}
	
	
	
}
