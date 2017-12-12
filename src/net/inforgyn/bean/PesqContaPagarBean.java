package net.inforgyn.bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.inforgyn.constante.SituacaoPagamentoEnum;
import net.inforgyn.constante.TipoPagamentoEnum;
import net.inforgyn.model.BaixaRecebimento;
import net.inforgyn.model.CartaoCredito;
import net.inforgyn.model.CategoriaDespesa;
import net.inforgyn.model.Cheque;
import net.inforgyn.model.Debito;
import net.inforgyn.model.DebitoCartao;
import net.inforgyn.model.DebitoCheque;
import net.inforgyn.model.filterPesquisa.FilterDebito;
import net.inforgyn.neg.DebitoNeg;
import net.inforgyn.util.NumericUtil;

@Named
@ViewScoped
public class PesqContaPagarBean implements PesquisaBean {

	private CartaoCredito cartao;
	@Inject	private List<CartaoCredito> cartoes;
	private CategoriaDespesa categoria;
	private Cheque cheque;
	@Inject	private List<Cheque> cheques;
	private Date dataEmissaoIni;
	private Date dataEmissaoFim;
	private Date dataVencimentoFim;
	private Date dataVencimentoIni;
	@Inject	private DebitoNeg debitoNeg;
	private List<Debito> debitos;
	private String descricao;
	@Inject	private List<CategoriaDespesa> listaCategorias;
	private SituacaoPagamentoEnum situacao;
	private TipoPagamentoEnum tipoPagamento;

	private Map<String, BigDecimal> valores;

	@Override
	public String cadastrar() {
		return "pretty:cadContaPagar";
	}

	public String styleAtraso(Debito debito) {
		return debito.getSituacao().equals(SituacaoPagamentoEnum.ATRASO) ? "atraso"
				: "";
	}

	public List<CartaoCredito> cartoes() {
		return cartoes;
	}

	public String detalhePagamento(Debito debito) {
		if (debito instanceof DebitoCartao) {
			return "/" + ((DebitoCartao) debito).getCartao().getDescricao();
		} else if (debito instanceof DebitoCheque) {
			return "/"
					+ ((DebitoCheque) debito).getEmitirCheque().getCheque()
							.getDescricao();
		}
		return "";
	}

	public CartaoCredito getCartao() {
		return cartao;
	}

	public List<CartaoCredito> getCartoes() {
		return cartoes;
	}

	public CategoriaDespesa getCategoria() {
		return categoria;
	}

	public Cheque getCheque() {
		return cheque;
	}

	public List<Cheque> getCheques() {
		return cheques;
	}

	public Date getDataVencimentoFim() {
		return dataVencimentoFim;
	}

	public Date getDataVencimentoIni() {
		return dataVencimentoIni;
	}

	public List<Debito> getDebitos() {
		return debitos;
	}

	public String getDescricao() {
		return descricao;
	}

	public boolean getMostrarCartao() {
		if (TipoPagamentoEnum.CARTAO.equals(tipoPagamento)) {
			if (cartoes == null || cartoes.isEmpty()) {
				return false;
			}
			return true;
		} else {
			cartao = null;
			return false;
		}
	}

	public Date getDataEmissaoIni() {
		return dataEmissaoIni;
	}

	public void setDataEmissaoIni(Date dataEmissaoIni) {
		this.dataEmissaoIni = dataEmissaoIni;
	}

	public Date getDataEmissaoFim() {
		return dataEmissaoFim;
	}

	public void setDataEmissaoFim(Date dataEmissaoFim) {
		this.dataEmissaoFim = dataEmissaoFim;
	}

	public boolean getMostrarCheque() {
		if (TipoPagamentoEnum.CHEQUE.equals(tipoPagamento)) {
			if (cheques == null || cheques.isEmpty()) {
				return false;
			}
			return true;
		} else {
			cheque = null;
			return false;
		}
	}

	public boolean getMostrarTotal() {
		return debitos != null && debitos.size() > 0;
	}

	public SituacaoPagamentoEnum getSituacao() {
		return situacao;
	}

	public TipoPagamentoEnum getTipoPagamento() {
		return tipoPagamento;
	}

	public String getValorAberto() {
		if (valores != null) {
			BigDecimal total = valores.get("total");
			if (total == null) {
				total = new BigDecimal(0);
			}
			BigDecimal pago = valores.get("pago");
			if (pago == null) {
				pago = new BigDecimal(0);
			}
			return NumericUtil.moeda(total.subtract(pago));
		}
		return "";
	}

	public String getValorTotal() {
		if (valores != null) {
			return NumericUtil.moeda((BigDecimal) valores.get("total"));
		}
		return "";
	}

	public List<CategoriaDespesa> listarCategorias() {
		return listaCategorias;
	}

	public List<Debito> listarDebitos() {
		return debitos;
	}

	public SelectItem[] listarSituacaoPagamentos() {
		SelectItem[] itens = new SelectItem[SituacaoPagamentoEnum.values().length];
		int i = 0;
		for (SituacaoPagamentoEnum e : SituacaoPagamentoEnum.values()) {
			// if(!e.equals(SituacaoPagamentoEnum.QUITADO)){
			itens[i++] = new SelectItem(e, e.getDescricao());
			// }
		}
		return itens;
	}

	public SelectItem[] listarTiposPagamentos() {
		SelectItem[] itens = new SelectItem[TipoPagamentoEnum.values().length];
		int i = 0;
		for (TipoPagamentoEnum e : TipoPagamentoEnum.values()) {
			itens[i++] = new SelectItem(e, e.getDescricao());
		}
		return itens;
	}

	@Override
	public String novo() {
		return "pretty:pesqContaPagar";
	}

	@Override
	public void pesquisar() {
		FilterDebito filtro = new FilterDebito(dataEmissaoIni, dataEmissaoFim,
				dataVencimentoFim, dataVencimentoIni, descricao, cheque,
				situacao, categoria, tipoPagamento, cartao);

		debitos = debitoNeg.listarDebitos(filtro);
		valores = debitoNeg.valorTotalPesquisa(filtro);
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

	public void setDataVencimentoFim(Date dataVencimentoFim) {
		this.dataVencimentoFim = dataVencimentoFim;
	}

	public void setDataVencimentoIni(Date dataVencimentoIni) {
		this.dataVencimentoIni = dataVencimentoIni;
	}

	public void setDebitos(List<Debito> debitos) {
		this.debitos = debitos;
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
