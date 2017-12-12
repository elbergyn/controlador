package net.inforgyn.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.inforgyn.constante.SituacaoPagamentoEnum;
import net.inforgyn.constante.TipoPagamentoEnum;
import net.inforgyn.decorator.DataMenu;
import net.inforgyn.model.CartaoCredito;
import net.inforgyn.model.CategoriaDespesa;
import net.inforgyn.model.Cheque;
import net.inforgyn.model.ContaPagar;
import net.inforgyn.model.Debito;
import net.inforgyn.model.DebitoCartao;
import net.inforgyn.model.DebitoCheque;
import net.inforgyn.model.EmitirCheque;
import net.inforgyn.model.Parcela;
import net.inforgyn.neg.CartaoCreditoNeg;
import net.inforgyn.neg.CategoriaDespesaNeg;
import net.inforgyn.neg.ChequeNeg;
import net.inforgyn.neg.ContaPagarNeg;
import net.inforgyn.neg.DebitoNeg;
import net.inforgyn.qualifierCdi.EmitirChequeQ;
import net.inforgyn.solid.Money;
import net.inforgyn.util.DataUtil;
import net.inforgyn.util.faces.FacesUtil;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;

@Named
@ViewScoped
public class CadastroContaPagarBean implements CadastroBean, Serializable {

	private static final long serialVersionUID = 1L;
	@Inject	private CartaoCredito cartao;
	@Inject	private CartaoCreditoNeg cartaoNeg;
	@Inject	private List<CartaoCredito> cartoes;
	@Inject	private CategoriaDespesaNeg categoriaNeg;
	@Inject	private ChequeNeg chequeNeg;
	@Inject	private List<Cheque> cheques;
	@Inject	private ContaPagar conta;
	@Inject	private ContaPagarNeg contaNeg;
	@Inject	private List<ContaPagar> contas;
	@Inject	private DebitoNeg debitoNeg;
	@Inject	@EmitirChequeQ	private EmitirCheque emitirCheque;
	private Boolean desabilitarVencimento = false;
	private Boolean mostrarPainelCartao = false;
	private Boolean mostrarPainelCheque = false;
	private Boolean desabilitarParcelamento = true;
	//private Boolean novoParcelamento = true;
	private BigDecimal valorParcela;

	public void adicionarNumeroCheque() {
		if (emitirCheque.getNumeros().size() >= conta.getParcelaTotal()) {
			FacesUtil
					.alertMessageSimples("Total de cheques já inseridos para o número de parcelas");
		} else {
			debitoNeg.varificarChequeEmitido(emitirCheque);

			emitirCheque.getNumeros().add(emitirCheque.getNumeroCheque());
			emitirCheque.setNumeroCheque(null);

		}
	}

	public Boolean getDesabilitarParcelamento() {
		return desabilitarParcelamento;
	}

	public void atualizarContas() {
		contas = contaNeg.listarContaAbertas();
	}

	public void verificarDesabilitarParcelamento() {
		desabilitarParcelamento = conta.getValorTotal() == null 
				|| conta.getValorTotal().longValue() ==  0 
				|| valorParcela == null
				|| valorParcela.longValue() == 0
				|| conta.getVencimento() == null
				|| conta.getParcelaTotal() == 1;
	}

	public void excluir() {
		contaNeg.excluir(conta);
		contas.remove(conta);
		FacesUtil.infoMessageSimples("Excluído: " + this.conta.getDescricao());
		novo();
	}

	public CartaoCredito getCartao() {
		return cartao;
	}

	public List<CartaoCredito> getCartoes() {
		return cartoes;
	}

	public List<Cheque> getCheques() {
		return cheques;
	}

	public ContaPagar getConta() {
		return conta;
	}

	public Date getDataAtual() {
		return DataUtil.getDataAtual();
	}

	public Boolean getDesabilitarVencimento() {
		return desabilitarVencimento;
	}

	public EmitirCheque getEmitirCheque() {
		return emitirCheque;
	}

	public List<CategoriaDespesa> getListarCategorias() {
		return categoriaNeg.listarCategorias();
	}

	public Boolean getMostrarPainelCartao() {
		return mostrarPainelCartao;
	}

	public Boolean getMostrarPainelCheque() {
		return mostrarPainelCheque;
	}

	public void calcularValorParcela() {
		Money[] valores = Money.valueOf(conta.getValorTotal()).dividir(
				conta.getParcelaTotal());
		valorParcela = valores[0].getBigDecimal();
	}

	public BigDecimal getValorParcela() {
		return valorParcela;
	}

	public List<DataMenu> getVencimentosCartao() {
		List<DataMenu> vencimentosCartao = new ArrayList<DataMenu>();
		if (conta.getVencimento() != null && conta.getVencimento().equals("")) {
			vencimentosCartao.add(new DataMenu(conta.getVencimento()));
		}
		if (cartao.getDiaVencimento() != null) {
			vencimentosCartao = cartaoNeg.verificarVencimentoCartao(cartao);
		}
		return vencimentosCartao;
	}

	public List<ContaPagar> listarContas() {
		return contas;
	}

	public SelectItem[] listarTiposPagamentos() {
		SelectItem[] itens = new SelectItem[TipoPagamentoEnum.values().length];
		int i = 0;
		for (TipoPagamentoEnum e : TipoPagamentoEnum.values()) {
			itens[i++] = new SelectItem(e, e.getDescricao());
		}
		return itens;
	}

	public void mostrarPainel() {
		if (conta.getTipoPagamento() == TipoPagamentoEnum.CHEQUE) {
			mostrarPainelCartao = false;
			desabilitarVencimento = false;
			cartao = new CartaoCredito();
			if (cheques != null && cheques.isEmpty()) {
				mostrarPainelCheque = false;
				FacesUtil
						.alertMessageSimples("Não há cheque cadastrado, realize o cadastro do cheque");
			} else {
				mostrarPainelCheque = true;
			}
		} else if (conta.getTipoPagamento() == TipoPagamentoEnum.CARTAO) {
			mostrarPainelCheque = false;
			emitirCheque = new EmitirCheque();
			desabilitarVencimento = true;
			if (cartoes != null && cartoes.isEmpty()) {
				mostrarPainelCartao = false;
				FacesUtil
						.alertMessageSimples("Não há cartão cadastrado, realize o cadastro do cartão");
			} else {
				mostrarPainelCartao = true;
			}
		} else {
			mostrarPainelCheque = false;
			mostrarPainelCartao = false;
			desabilitarVencimento = false;
			cartao = new CartaoCredito();
			emitirCheque = new EmitirCheque();
		}
	}

	@Override
	public void novo() {
		conta = new ContaPagar();
		cartao = new CartaoCredito();
		emitirCheque = new EmitirCheque();
		valorParcela = null;
		desabilitarVencimento = false;
		mostrarPainelCartao = false;
		mostrarPainelCheque = false;
		desabilitarParcelamento = true;
		//novoParcelamento = true;
	}

	public void gerarParcelamento() {
		//if(novoParcelamento){
			contaNeg.calcularParcelamento(conta);
		//}
		verificarDesabilitarParcelamento();
	}

	public void onCellEdit(CellEditEvent event) {
		Date novo = (Date) event.getNewValue();
		Date old = (Date) event.getOldValue();
		DataTable dataModel = (DataTable) event.getSource();
		Parcela parcela = (Parcela) dataModel.getRowData();

		parcela.setVencimento(novo);
		FacesUtil.alertMessageSimples("Será alterado o vencimento da parcela "
				+ parcela.getNumero() + " de " + DataUtil.getDataPadraoString(old)
				+ " para " + DataUtil.getDataPadraoString(novo));
	}

	public void calcularValores() {
		//realizarNovoParcelamento();
		if (conta.getValorTotal() != null
				&& conta.getValorTotal().longValue() > 0) {
			calcularValorParcela();
		} else if (conta.getValorParcelas().size() > 0) {
			calcularValorTotal();
		}
		verificarDesabilitarParcelamento();
	}
	
	/*public void realizarNovoParcelamento(){
		novoParcelamento = true;
	}*/

	public void calcularValorTotal() {
		//realizarNovoParcelamento();
		conta.setValorTotal(valorParcela.multiply(new BigDecimal(conta
				.getParcelaTotal())));
		verificarDesabilitarParcelamento();
	}

	public boolean permitidoAlterar(ContaPagar conta) {
		if (conta.getSituacao().equals(SituacaoPagamentoEnum.A_VENCER)
				|| conta.getSituacao().equals(SituacaoPagamentoEnum.ATRASO)) {
			return !true;
		} else {
			return !false;
		}
	}

	public void popularOpcoesPagamento() {
		ContaPagar pagar = contaNeg.carregarDebitos(conta);
		if (TipoPagamentoEnum.CHEQUE.equals(conta.getTipoPagamento())) {
			DebitoCheque debito = (DebitoCheque) pagar.getDebitos().get(0);
			emitirCheque = debito.getEmitirCheque();

			for (Debito d : pagar.getDebitos()) {
				emitirCheque.getNumeros().add(
						((DebitoCheque) d).getEmitirCheque().getNumeroCheque());
			}
			emitirCheque.setNumeroCheque(null);

		} else if (TipoPagamentoEnum.CARTAO.equals(conta.getTipoPagamento())) {
			cartao = ((DebitoCartao) pagar.getDebitos().get(0)).getCartao();
		}
	}

	public void removerNumeroCheque(Long numero) {
		emitirCheque.getNumeros().remove(numero);
	}

	public boolean requererNumeroCheque() {
		if (emitirCheque.getNumeros().size() > 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void salvar() {
		gerarParcelamento();
		if (conta.getId() == null) {
			contaNeg.salvar(conta, cartao, emitirCheque);
			contas.add(conta);
			FacesUtil.infoMessageSimples("Conta cadastrada: "
					+ this.conta.getDescricao());
		} else {
			contaNeg.alterar(conta, cartao, emitirCheque);
			FacesUtil.infoMessageSimples("Conta alterada: "
					+ this.conta.getDescricao());
		}
		novo();
	}

	public void setCartao(CartaoCredito cartao) {
		this.cartao = cartao;
	}

	public void setConta(ContaPagar conta) {
		this.conta = contaNeg.carregarParcelamento(conta);
		valorParcela = this.conta.getValorParcelas().get(0).getValor().getBigDecimal();
		//novoParcelamento = false;
		verificarDesabilitarParcelamento();
	}

	public void setEmitirCheque(EmitirCheque emitirCheque) {
		this.emitirCheque = emitirCheque;
	}

	public void setValorParcela(BigDecimal valorParcela) {
		this.valorParcela = valorParcela;
	}

	public String styleAtraso(ContaPagar conta) {
		return conta.getSituacao().equals(SituacaoPagamentoEnum.ATRASO) ? "atraso"
				: "";
	}
}
