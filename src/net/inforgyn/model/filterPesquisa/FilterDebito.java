package net.inforgyn.model.filterPesquisa;

import java.util.Date;

import net.inforgyn.constante.SituacaoPagamentoEnum;
import net.inforgyn.constante.TipoPagamentoEnum;
import net.inforgyn.model.CartaoCredito;
import net.inforgyn.model.CategoriaDespesa;
import net.inforgyn.model.Cheque;

public class FilterDebito {
	private CartaoCredito cartao;
	private CategoriaDespesa categoria;
	private Cheque cheque;
	private Date dataEmissaoFim;
	private Date dataEmissaoIni;
	private Date dataVencimentoFim;
	private Date dataVencimentoIni;
	private String descricao;
	private SituacaoPagamentoEnum situacao;
	private TipoPagamentoEnum tipoPagamento;
	
	public FilterDebito(Date dataEmissaoIni, Date dataEmissaoFim,
			Date dataVencimentoFim, Date dataVencimentoIni, String descricao,
			Cheque cheque, SituacaoPagamentoEnum situacao,
			CategoriaDespesa categoria, TipoPagamentoEnum tipoPagamento, CartaoCredito cartao) {
		super();
		this.dataEmissaoIni = dataEmissaoIni;
		this.dataEmissaoFim = dataEmissaoFim;
		this.dataVencimentoFim = dataVencimentoFim;
		this.dataVencimentoIni = dataVencimentoIni;
		this.descricao = descricao;
		this.cheque = cheque;
		this.situacao = situacao;
		this.categoria = categoria;
		this.tipoPagamento = tipoPagamento;
		this.cartao = cartao;
	}
	public CartaoCredito getCartao() {
		return cartao;
	}
	public CategoriaDespesa getCategoria() {
		return categoria;
	}
	public Cheque getCheque() {
		return cheque;
	}
	public Date getDataEmissaoFim() {
		return dataEmissaoFim;
	}
	public Date getDataEmissaoIni() {
		return dataEmissaoIni;
	}
	public Date getDataVencimentoFim() {
		return dataVencimentoFim;
	}
	public Date getDataVencimentoIni() {
		return dataVencimentoIni;
	}
	public String getDescricao() {
		return descricao;
	}
	public SituacaoPagamentoEnum getSituacao() {
		return situacao;
	}
	public TipoPagamentoEnum getTipoPagamento() {
		return tipoPagamento;
	}
	public void setCartao(CartaoCredito cartao) {
		this.cartao = cartao;
	}
	public void setCategoria(CategoriaDespesa categoria) {
		this.categoria = categoria;
	}
	public void setCheque(Cheque cheque) {
		this.cheque = cheque;
	}
	public void setDataEmissaoFim(Date dataEmissaoFim) {
		this.dataEmissaoFim = dataEmissaoFim;
	}
	public void setDataEmissaoIni(Date dataEmissaoIni) {
		this.dataEmissaoIni = dataEmissaoIni;
	}
	public void setDataVencimentoFim(Date dataVencimentoFim) {
		this.dataVencimentoFim = dataVencimentoFim;
	}
	public void setDataVencimentoIni(Date dataVencimentoIni) {
		this.dataVencimentoIni = dataVencimentoIni;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public void setSituacao(SituacaoPagamentoEnum situacao) {
		this.situacao = situacao;
	}
	public void setTipoPagamento(TipoPagamentoEnum tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}
}
