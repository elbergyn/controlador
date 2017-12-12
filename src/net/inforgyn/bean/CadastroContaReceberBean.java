package net.inforgyn.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;

import net.inforgyn.constante.SituacaoPagamentoEnum;
import net.inforgyn.constante.TipoRecebimentoEnum;
import net.inforgyn.model.ContaReceber;
import net.inforgyn.model.Credito;
import net.inforgyn.model.CreditoCheque;
import net.inforgyn.model.Devedor;
import net.inforgyn.model.Parcela;
import net.inforgyn.neg.ContaReceberNeg;
import net.inforgyn.neg.DevedorNeg;
import net.inforgyn.solid.Money;
import net.inforgyn.util.DataUtil;
import net.inforgyn.util.faces.FacesUtil;

@Named
@ViewScoped
public class CadastroContaReceberBean implements CadastroBean, Serializable{

	private static final long serialVersionUID = 1L;
		
	private Boolean addDevedor = false;
	@Inject private ContaReceber conta;
	@Inject private ContaReceberNeg contaNeg;
	@Inject private List<ContaReceber> contas;
	@Inject private List<Devedor> devedores;
	@Inject private DevedorNeg devedorNeg;
	private Long numeroCheque;
	//private Boolean novoParcelamento = true;
	private Boolean desabilitarParcelamento = true;
	private BigDecimal valorParcela;
	
	private boolean painelDevedor = false;

	public void addNumeroCheque(){
		if (conta.getNumerosCheque().size() >= conta.getParcelaTotal()) {
			FacesUtil.alertMessageSimples("Total de cheques já inseridos para o número de parcelas");
		}else{
			conta.getNumerosCheque().add(numeroCheque);
			numeroCheque = null;
		}
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
	
	public void atualizarDevedores(){
		devedores = devedorNeg.listarDevedores();
	}
	
	public void excluir(){
		contaNeg.excluir(conta);
		contas.remove(conta);
		FacesUtil.infoMessageSimples("Excluído: " + this.conta.getDescricao());
		novo();
	}

	public Boolean getAddDevedor() {
		return addDevedor;
	}
	
	public ContaReceber getConta() {
		return conta;
	}

	public Date getDataAtual(){
		return DataUtil.getDataAtual();
	}

	public List<Devedor> getDevedores() {
		return devedores;
	}

	public Boolean getMostrarPanelCheques(){
		if (conta.getTipoRecebimento() != null &&
				conta.getTipoRecebimento().equals(TipoRecebimentoEnum.CHEQUE)){
			return true;
		}
		return false;
	}
	
	public Long getNumeroCheque() {
		return numeroCheque;
	}
	
	public boolean getRequererNumeroCheque() {
		if (TipoRecebimentoEnum.CHEQUE.equals(conta.getTipoRecebimento())
				&& conta.getNumerosCheque().size() == 0) {
			return true;
		}
		return false;
	}
	
	@PostConstruct
	public void init(){
	}
	
	public List<ContaReceber> listarContas(){
		contas = contaNeg.listarContasAbertas();
		return contas;
	}

	public List<Devedor> listarDevedores(String filtro){
		List<Devedor> filtrados = new ArrayList<Devedor>();
		for(Devedor d : devedores){
			if(d.getDescricao().toLowerCase().startsWith(filtro.toLowerCase())){
				filtrados.add(d);
			}
		}
		return filtrados;
	}
	
	public SelectItem[] listarTiposRecebimentos(){
		SelectItem[] itens = new SelectItem[TipoRecebimentoEnum.values().length];
		int i = 0;
		for (TipoRecebimentoEnum e : TipoRecebimentoEnum.values()) {
			itens[i++] = new SelectItem(e, e.getDescricao());
		}
		return itens;
	}
	
	public void mostrarDevedor(){
		painelDevedor = true;
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
	
	public void calcularValorParcela() {
		Money[] valores = Money.valueOf(conta.getValorTotal()).dividir(
				conta.getParcelaTotal());
		valorParcela = valores[0].getBigDecimal();
	}
	
	public void calcularValorTotal() {
		//realizarNovoParcelamento();
		conta.setValorTotal(valorParcela.multiply(new BigDecimal(conta
				.getParcelaTotal())));
		verificarDesabilitarParcelamento();
	}
	
	@Override
	public void novo() {
		conta = new ContaReceber();
		numeroCheque = null;
		//novoParcelamento = true;
		desabilitarParcelamento = true;
		valorParcela = null;
	}
	
	public BigDecimal getValorParcela() {
		return valorParcela;
	}

	public void setValorParcela(BigDecimal valorParcela) {
		this.valorParcela = valorParcela;
	}

	public Boolean getDesabilitarParcelamento() {
		return desabilitarParcelamento;
	}

	public void ocultarDevedor(){
		painelDevedor = false;
	}
	
	public boolean painelDevedor(){
		return painelDevedor;
	}

	public boolean permitidoAlterar(ContaReceber conta){
		if (conta.getSituacao().equals(SituacaoPagamentoEnum.A_VENCER)
				|| conta.getSituacao().equals(SituacaoPagamentoEnum.ATRASO)) {
			return !true;
		}else{
			return !false;
		}
	}
	public void popularOpcoesPagamento() {
		if (TipoRecebimentoEnum.CHEQUE.equals(conta.getTipoRecebimento())) {
			ContaReceber receber = contaNeg.carregarCreditos(conta);
			for (Credito d : receber.getCreditos()) {
				conta.getNumerosCheque().add(((CreditoCheque)d).getNumeroCheque());
			}
		}
	}
	
	public void removeNumeroCheque(Long numero){
		conta.getNumerosCheque().remove(numero);
	}
	
	public void requererDevedor() {
		this.addDevedor = true;
	}
	
	public void gerarParcelamento() {
		//if(novoParcelamento){
			contaNeg.calcularParcelamento(conta);
		//}
		verificarDesabilitarParcelamento();
	}
	
	public void verificarDesabilitarParcelamento() {
		desabilitarParcelamento = conta.getValorTotal() == null 
				|| conta.getValorTotal().longValue() ==  0 
				|| valorParcela == null
				|| valorParcela.longValue() == 0
				|| conta.getVencimento() == null
				|| conta.getParcelaTotal() == 1;
	}
	
	@Override
	public void salvar() {
		if(conta.getParcelaTotal() > 0){
			gerarParcelamento();
		}
		if(conta.getId() == null){
			conta = contaNeg.salvar(conta);
			contas.add(conta);
			FacesUtil.infoMessageSimples("Conta a receber cadastrada: "+this.conta.getDescricao());
		}else {
			conta = contaNeg.alterar(conta);
			FacesUtil.infoMessageSimples("Conta alterada: "+this.conta.getDescricao());
		}
		novo();
	}
	
	public void setAddDevedor(Boolean addDevedor) {
		this.addDevedor = addDevedor;
	}
	
	public void setConta(ContaReceber conta) {
		this.conta = contaNeg.carregarParcelamento(conta);
		valorParcela = this.conta.getValorParcelas().get(0).getValor().getBigDecimal();
		//novoParcelamento = false;
		verificarDesabilitarParcelamento();
	}	

	public void setDevedores(List<Devedor> devedores) {
		this.devedores = devedores;
	}

	public void setNumeroCheque(Long numeroCheque) {
		this.numeroCheque = numeroCheque;
	}
	
	public String styleAtraso(ContaReceber conta){
		return conta.getSituacao().equals(SituacaoPagamentoEnum.ATRASO) ? "atraso" : "";
	}
}
