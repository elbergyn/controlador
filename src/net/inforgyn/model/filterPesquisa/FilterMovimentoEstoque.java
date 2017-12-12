package net.inforgyn.model.filterPesquisa;

import java.util.Date;

import net.inforgyn.constante.TipoLancamentoEnum;

public class FilterMovimentoEstoque {
	private Date dataInicio;
	private Date dataFim;
	private String descricaoProd;
	private TipoLancamentoEnum tipoLancamento;
	
	public FilterMovimentoEstoque() {
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

	public TipoLancamentoEnum getTipoLancamento() {
		return tipoLancamento;
	}

	public void setTipoLancamento(TipoLancamentoEnum tipoLancamento) {
		this.tipoLancamento = tipoLancamento;
	}
}
