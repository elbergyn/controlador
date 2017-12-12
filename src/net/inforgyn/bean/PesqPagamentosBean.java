package net.inforgyn.bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.inforgyn.constante.SituacaoPagamentoEnum;
import net.inforgyn.constante.TipoPagamentoEnum;
import net.inforgyn.model.BaixaPagamento;
import net.inforgyn.model.CategoriaDespesa;
import net.inforgyn.model.Debito;
import net.inforgyn.model.DebitoCartao;
import net.inforgyn.model.DebitoCheque;
import net.inforgyn.model.filterPesquisa.FilterPagamento;
import net.inforgyn.neg.BaixaPagamentoNeg;
import net.inforgyn.util.NumericUtil;

@Named
@ViewScoped
public class PesqPagamentosBean implements PesquisaBean {

	private CategoriaDespesa categoria;
	private String descricao;
	private Date fimPagamento;
	private Date fimVencimento;
	private Date inicioPagamento;
	private Date inicioVencimento;
	@Inject	private List<CategoriaDespesa> listaCategorias;
	@Inject	private BaixaPagamentoNeg pagamentoNeg;
	private List<BaixaPagamento> pagamentos;
	private SituacaoPagamentoEnum situacao;
	private TipoPagamentoEnum tipoPagamento;
	private Map<String, BigDecimal> valores;

	@Override
	public String cadastrar() {
		return "pretty:cadBaixaPagamento";
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

	public CategoriaDespesa getCategoria() {
		return categoria;
	}

	public String getDescricao() {
		return descricao;
	}

	public Date getFimPagamento() {
		return fimPagamento;
	}

	public Date getFimVencimento() {
		return fimVencimento;
	}

	public Date getInicioPagamento() {
		return inicioPagamento;
	}

	public Date getInicioVencimento() {
		return inicioVencimento;
	}

	public boolean getMostrarTotal() {
		return pagamentos != null && pagamentos.size() > 0;
	}

	public List<BaixaPagamento> getPagamentos() {
		return pagamentos;
	}

	public SituacaoPagamentoEnum getSituacao() {
		return situacao;
	}

	public TipoPagamentoEnum getTipoPagamento() {
		return tipoPagamento;
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

	public SelectItem[] listarSituacaoPagamentos() {
		SelectItem[] itens = new SelectItem[2];
		itens[0] = new SelectItem(SituacaoPagamentoEnum.PAG_PARCIAL,
				SituacaoPagamentoEnum.PAG_PARCIAL.getDescricao());
		itens[1] = new SelectItem(SituacaoPagamentoEnum.QUITADO,
				SituacaoPagamentoEnum.QUITADO.getDescricao());

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
		return "pretty:pesqPagamentos";
	}

	@Override
	public void pesquisar() {
		FilterPagamento filtro = new FilterPagamento(descricao, tipoPagamento,
				inicioPagamento, fimPagamento, inicioVencimento, fimVencimento,
				situacao, categoria);

		pagamentos = pagamentoNeg.pesquisarPagamentos(filtro);
		valores = pagamentoNeg.valorTotalPesquisa(filtro);
	}

	public void setCategoria(CategoriaDespesa categoria) {
		this.categoria = categoria;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setFimPagamento(Date fimPagamento) {
		this.fimPagamento = fimPagamento;
	}

	public void setFimVencimento(Date fimVencimento) {
		this.fimVencimento = fimVencimento;
	}

	public void setInicioPagamento(Date inicioPagamento) {
		this.inicioPagamento = inicioPagamento;
	}

	public void setInicioVencimento(Date inicioVencimento) {
		this.inicioVencimento = inicioVencimento;
	}

	public void setPagamentos(List<BaixaPagamento> pagamentos) {
		this.pagamentos = pagamentos;
	}

	public void setSituacao(SituacaoPagamentoEnum situacao) {
		this.situacao = situacao;
	}

	public void setTipoPagamento(TipoPagamentoEnum tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}

}
