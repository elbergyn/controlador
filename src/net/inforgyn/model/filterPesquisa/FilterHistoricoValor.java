package net.inforgyn.model.filterPesquisa;

import java.util.Date;

import net.inforgyn.model.Produto;

public class FilterHistoricoValor {
	private Date dataInicio;
	private Date dataFim;
	private String descricaoProd;
	
	public FilterHistoricoValor() {
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public String getDescricaoProd() {
		return descricaoProd;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public void setDescricaoProd(String descricaoProd) {
		this.descricaoProd = descricaoProd;
	}
}
