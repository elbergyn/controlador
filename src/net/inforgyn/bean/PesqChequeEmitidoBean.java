package net.inforgyn.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.inforgyn.constante.SituacaoPagamentoEnum;
import net.inforgyn.constante.TipoPagamentoEnum;
import net.inforgyn.model.CategoriaDespesa;
import net.inforgyn.model.Cheque;
import net.inforgyn.model.DebitoCheque;
import net.inforgyn.model.filterPesquisa.FilterDebito;
import net.inforgyn.neg.DebitoNeg;
import net.inforgyn.util.NumericUtil;

@Named
@ViewScoped
public class PesqChequeEmitidoBean implements PesquisaBean {

	private CategoriaDespesa categoria;
	private Cheque cheque;
	@Inject
	private List<Cheque> cheques;

	private List<DebitoCheque> chequesEmitidos = new ArrayList<DebitoCheque>();
	private Date dataEmissaoFim;
	private Date dataEmissaoIni;
	private Date dataVencimentoFim;
	private Date dataVencimentoIni;
	@Inject
	private DebitoNeg debitoNeg;
	private String descricao;
	@Inject
	private List<CategoriaDespesa> listaCategorias;
	private SituacaoPagamentoEnum situacao;
	private Map<String, BigDecimal> valores;

	@Override
	public String cadastrar() {
		return "pretty:cadContaPagar";
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

	public List<DebitoCheque> getChequesEmitidos() {
		return chequesEmitidos;
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

	public boolean getMostrarTotal() {
		return chequesEmitidos != null && chequesEmitidos.size() > 0;
	}

	public SituacaoPagamentoEnum getSituacao() {
		return situacao;
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

	public SelectItem[] listarSituacaoPagamentos() {
		SelectItem[] itens = new SelectItem[SituacaoPagamentoEnum.values().length];
		int i = 0;
		for (SituacaoPagamentoEnum e : SituacaoPagamentoEnum.values()) {
			itens[i++] = new SelectItem(e, e.getDescricao());
		}
		return itens;
	}

	@Override
	public String novo() {
		return "pretty:pesqChequeEmitido";
	}

	@Override
	public void pesquisar() {
		FilterDebito filtro = new FilterDebito(dataEmissaoIni,
				dataEmissaoFim, dataVencimentoFim, dataVencimentoIni,
				descricao, cheque, situacao, categoria,
				TipoPagamentoEnum.CHEQUE, null);

		chequesEmitidos = debitoNeg.listarChequesEmitidos(filtro);
		valores = debitoNeg.valorTotalPesquisa(filtro);
	}

	public void setCategoria(CategoriaDespesa categoria) {
		this.categoria = categoria;
	}

	public void setCheque(Cheque cheque) {
		this.cheque = cheque;
	}

	public void setCheques(List<Cheque> cheques) {
		this.cheques = cheques;
	}

	public void setChequesEmitidos(List<DebitoCheque> chequesEmitidos) {
		this.chequesEmitidos = chequesEmitidos;
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

	public String styleAtraso(DebitoCheque cheque) {
		return cheque.getSituacao().equals(SituacaoPagamentoEnum.ATRASO) ? "atraso"
				: "";
	}
}
