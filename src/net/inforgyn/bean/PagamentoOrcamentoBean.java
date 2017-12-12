package net.inforgyn.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import net.inforgyn.constante.StatusVendaEnum;
import net.inforgyn.model.TipoRecebimento;
import net.inforgyn.model.Venda;
import net.inforgyn.model.VendaPagamento;
import net.inforgyn.neg.OrcamentoNeg;
import net.inforgyn.neg.VendaNeg;
import net.inforgyn.solid.Money;
import net.inforgyn.util.NumericUtil;
import net.inforgyn.util.UsuarioSessaoUtil;
import net.inforgyn.util.faces.FacesUtil;

@Named
@ViewScoped
public class PagamentoOrcamentoBean implements CadastroBean, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject	private OrcamentoNeg orcamentoNeg;
	private VendaPagamento pagamento;
	@Inject private VendaNeg vendaNeg; 
	@Inject	private List<TipoRecebimento> tiposRecebimento;
	private BigDecimal valorPagar;
	private BigDecimal valorPago;
	private Venda venda;
	private BigDecimal troco;
	
	@PostConstruct
	private void init(){
		//limpar();
		pagamento = new VendaPagamento();
		venda = new Venda();
	}

	public void adicionarPagamento() {
		if(valorPagar.equals(0)){
			FacesUtil.alertMessageSimples("Não há valor em aberto para ser adicionado");
			valorPago = new BigDecimal(0);
		}else if (pagamento.getTipoRecebimento() != null) {
			BigDecimal valorRestante = venda.getValorTotal().subtract(venda.getValorDesconto().add(venda.getValorPago()));
			if(valorPagar.doubleValue() > valorRestante.doubleValue() 
					&& !pagamento.getTipoRecebimento().getDescricao().equals("Dinheiro")){
				calcularValorPagar();
				FacesUtil.alertMessageSimples("Valor a receber esta superior ao valor da divida");
				return;
			}
			
			if(valorPagar.doubleValue() > valorPago.doubleValue()){
				pagamento.setValor(valorPago);
			}else{
				pagamento.setValor(valorPagar);
			}			
			pagamento.setUsuario(UsuarioSessaoUtil.getUsuario());
			venda.getPagamentos().add(pagamento);
			calcularTroco();
			calcularValorPagar();
			pagamento = new VendaPagamento();
			valorPago = new BigDecimal(0);
		} else {
			if (pagamento.getTipoRecebimento() == null) {
				FacesUtil.alertMessageSimples("Informe o tipo de recebimento");
			}
		}
	}

	public BigDecimal getValorPago() {
		return valorPago;
	}

	public void setValorPago(BigDecimal valorPago) {
		this.valorPago = valorPago;
	}

	public void calcularDesconto() {
		if (venda.getPercentualDesconto() != null) {
			BigDecimal perc = new BigDecimal(venda.getPercentualDesconto());

			Money total = Money.valueOf(venda.getOrcamento().getValorTotal());
			Money desconto = total.multiplicar(perc);

			venda.setValorDesconto(desconto.getBigDecimal());
			venda.setValorTotal(total.subtrair(desconto).getBigDecimal());

			pagamento.setValor(total.subtrair(desconto).getBigDecimal());

			calcularValorPagar();
		}
	}

	public void calcularTaxaDesconto() {
		if (venda.getValorDesconto().doubleValue() > 0.0) {
			venda.setPercentualDesconto(venda.getValorDesconto().doubleValue()
					/ venda.getOrcamento().getValorTotal().doubleValue());
			calcularValorPagar();
		}
	}	

	public void calcularValorPagar() {
		valorPagar = venda.getValorTotal().subtract(venda.getValorDesconto().add(venda.getValorPago()));

		if (valorPagar.doubleValue() <= 0.0) {
			valorPagar = new BigDecimal(0);
		}

	}

	public String getValorPagarMoeda() {
		return NumericUtil.moeda(valorPagar);
	}
	
	public BigDecimal getValorPagar() {
		return valorPagar;
	}

	public void setValorPagar(BigDecimal valorPagar) {
		this.valorPagar = valorPagar;
	}

	public void carregarOrcamento() {
		if(venda.getOrcamento().getId() != null && venda.getOrcamento().getItensVenda().isEmpty()){
			this.venda.setOrcamento(orcamentoNeg.carregarLazy(venda.getOrcamento(),
				"funcionario", "itensVenda"));
		}
		this.venda.setValorTotal(venda.getOrcamento().getValorTotal());
		this.venda.getOrcamento().setStatus(StatusVendaEnum.CONCLUIDO);
		calcularValorPagar();
	}

	public VendaPagamento getPagamento() {
		return pagamento;
	}

	public List<TipoRecebimento> getTiposRecebimento() {
		return tiposRecebimento;
	}
	
	public void trocoDialog(){
		FacesUtil.messageInfoDialog("Troco", NumericUtil.moeda(troco));
		/*Map<String, Object> options = new HashMap<String, Object>();
		FacesUtil.abrirDialog("dialogTroco", options, null);*/
		
	}

	public void calcularTroco() {
		if(valorPago.doubleValue() > valorPagar.doubleValue()){
			troco = valorPago.subtract(valorPagar);
			trocoDialog();
		}else{
			troco = new BigDecimal(0);
		}
		valorPago = new BigDecimal(0);
	}
	
	public void carregarVendaDialog(Venda venda){
		this.venda = venda;
		carregarOrcamento();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("width", "60%");
		parametros.put("contentWidth", "100%");
		parametros.put("height", "80%");
		parametros.put("modal", true);
		parametros.put("includeViewParams", true);
		RequestContext.getCurrentInstance().openDialog("pagamentoVendaDialog", parametros, null);
	}
	
	public String getTroco(){
		return NumericUtil.moeda(troco);
	}

	public Venda getVenda() {
		return venda;
	}

	public void limpar() {
		venda = new Venda();
		pagamento = new VendaPagamento();
	}

	@Override
	public void novo() {
		limpar();
		//RedirecionarPaginaUtil.redirect("/orcamento/");
	}

	public void removerPagamento() {
		venda.getPagamentos().remove(pagamento);
		calcularValorPagar();
		pagamento = new VendaPagamento();
	}

	@Override
	public void salvar() {
		if (valorPagar.doubleValue() > 0) {
			FacesUtil.alertMessageSimples("Valor recebido menor que o valor da conta");
		} else {
			venda = vendaNeg.salvar(venda);
			
			FacesUtil.alertMessageSimples("Venda concluída");
			novo();
		}
	}
	
	public void salvarDialog(){
		if (valorPagar.doubleValue() > 0) {
			FacesUtil.alertMessageSimples("Valor recebido menor que o valor da conta");
		} else {
			venda = vendaNeg.salvar(venda);

			RequestContext.getCurrentInstance().closeDialog(venda);
			FacesUtil.alertMessageSimples("Venda concluída");
			novo();
		}
	}

	public void setPagamento(VendaPagamento pagamento) {
		this.pagamento = pagamento;
	}

	public void setTiposRecebimento(List<TipoRecebimento> tiposRecebimento) {
		this.tiposRecebimento = tiposRecebimento;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}
}
