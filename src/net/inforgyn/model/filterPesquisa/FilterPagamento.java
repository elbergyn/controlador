package net.inforgyn.model.filterPesquisa;

import java.util.Date;

import net.inforgyn.constante.SituacaoPagamentoEnum;
import net.inforgyn.constante.TipoPagamentoEnum;
import net.inforgyn.model.CategoriaDespesa;

public class FilterPagamento {
	private String descricao;
	private TipoPagamentoEnum tipoPagamento;
	private Date inicioPagamento;
	private Date fimPagamento;
	private Date inicioVencimento;
	private Date fimVencimento;
	private SituacaoPagamentoEnum situacao;
	private CategoriaDespesa categoria;
	
	public FilterPagamento(String descricao, TipoPagamentoEnum tipoPagamento,
			Date inicioPagamento, Date fimPagamento, Date inicioVencimento,
			Date fimVencimento, SituacaoPagamentoEnum situacao,
			CategoriaDespesa categoria) {
		super();
		this.descricao = descricao;
		this.tipoPagamento = tipoPagamento;
		this.inicioPagamento = inicioPagamento;
		this.fimPagamento = fimPagamento;
		this.inicioVencimento = inicioVencimento;
		this.fimVencimento = fimVencimento;
		this.situacao = situacao;
		this.categoria = categoria;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public TipoPagamentoEnum getTipoPagamento() {
		return tipoPagamento;
	}
	public void setTipoPagamento(TipoPagamentoEnum tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}
	public Date getInicioPagamento() {
		return inicioPagamento;
	}
	public void setInicioPagamento(Date inicioPagamento) {
		this.inicioPagamento = inicioPagamento;
	}
	public Date getFimPagamento() {
		return fimPagamento;
	}
	public void setFimPagamento(Date fimPagamento) {
		this.fimPagamento = fimPagamento;
	}
	public Date getInicioVencimento() {
		return inicioVencimento;
	}
	public void setInicioVencimento(Date inicioVencimento) {
		this.inicioVencimento = inicioVencimento;
	}
	public Date getFimVencimento() {
		return fimVencimento;
	}
	public void setFimVencimento(Date fimVencimento) {
		this.fimVencimento = fimVencimento;
	}
	public SituacaoPagamentoEnum getSituacao() {
		return situacao;
	}
	public void setSituacao(SituacaoPagamentoEnum situacao) {
		this.situacao = situacao;
	}
	public CategoriaDespesa getCategoria() {
		return categoria;
	}
	public void setCategoria(CategoriaDespesa categoria) {
		this.categoria = categoria;
	}
	
	
}
