package net.inforgyn.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;

import net.inforgyn.constante.SituacaoPagamentoEnum;
import net.inforgyn.model.BaixaPagamento;
import net.inforgyn.model.CartaoCredito;
import net.inforgyn.model.CategoriaDespesa;
import net.inforgyn.model.Cheque;
import net.inforgyn.model.filterPesquisa.FiltroBaixaPagamento;
import net.inforgyn.neg.BaixaPagamentoNeg;
import net.inforgyn.util.DataUtil;
import net.inforgyn.util.NumericUtil;
import net.inforgyn.util.faces.FacesUtil;

@Named
@ViewScoped
public class BaixaPagamentoBean implements CadastroBean, Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<BaixaPagamento> baixas;
	@Inject List<CartaoCredito> cartoes;
	@Inject List<Cheque> cheques;
	@Inject	private BaixaPagamentoNeg pagamentoNeg;
	private Boolean permitirPagamento = false;
	private List<BaixaPagamento> realizarBaixas;
	@Inject List<CategoriaDespesa> categorias;
	@Inject private FiltroBaixaPagamento filtro;
	
	public void carregarDebitos(FiltroBaixaPagamento filtro) {
		baixas = pagamentoNeg.gerarBaixaPagamento(filtro);
	}

	public FiltroBaixaPagamento getFiltro() {
		return filtro;
	}

	public void setFiltro(FiltroBaixaPagamento filtro) {
		this.filtro = filtro;
	}

	public void checked() {
		if (null != realizarBaixas && !realizarBaixas.isEmpty()) {
			permitirPagamento = true;
		}
	}
	
	public List<Integer> getAnos(){
		List<Integer> anos = pagamentoNeg.listarAnos();
		if(anos.size() == 0){
			anos.add(DataUtil.getAno());
		}
		return anos;
	}

	public void filtrar() {
		carregarDebitos(filtro);
	}

	public List<BaixaPagamento> getBaixas() {
		return baixas;
	}
	
	public List<CartaoCredito> getCartoes(){
    	return cartoes;
    }
	
	public List<Cheque> getCheques() {
		return cheques;
	}

	public SelectItem[] getMeses() {
		return DataUtil.getMeses();
	}

	public Boolean getPermitirPagamento() {
		return !permitirPagamento;
	}
	
	public List<BaixaPagamento> getRealizarBaixas() {
		return realizarBaixas;
	}
	
	public String getValorPagar(){
		BigDecimal valor = new BigDecimal(0);
		if(null != baixas && !baixas.isEmpty()){
			for(BaixaPagamento baixa : baixas){
				valor = valor.add(baixa.getValor());
			}
		}
		return NumericUtil.moeda(valor);
	}
	
	public String getValorTotal(){
		BigDecimal valor = new BigDecimal(0);
		if(null != baixas && !baixas.isEmpty()){
			for(BaixaPagamento baixa : baixas){
				valor = valor.add(baixa.getDebito().getValor());
			}
		}
		return NumericUtil.moeda(valor);
	}

	public List<CategoriaDespesa> getCategorias() {
		return categorias;
	}

	@PostConstruct
	public void init() {
		carregarDebitos(null);
	}

	public void limpar(){
		filtro = new FiltroBaixaPagamento();
		permitirPagamento = false;
		realizarBaixas = new ArrayList<BaixaPagamento>();
		carregarDebitos(null);
	}

	public boolean mostrarCartao(){
		return cartoes.isEmpty() ? false : true;
	}

	public boolean mostrarCheque(){
		return cheques.isEmpty() ? false : true;
	}	

	@Override
	public void novo() {
		limpar();
	}

	public void onCellEdit(CellEditEvent event) {
		BigDecimal valorAnt = (BigDecimal) event.getOldValue();
		BigDecimal novoValor = (BigDecimal) event.getNewValue();

		DataTable dataModel = (DataTable) event.getSource();
		BaixaPagamento baixa = (BaixaPagamento) dataModel.getRowData();

		if (novoValor.compareTo(valorAnt) > 0) {
			baixa.setValor(valorAnt);
			FacesUtil.alertMessageSimples("Valor de pagamento não deve ser maior que valor disponibilizado para pagamento");
			Integer row = event.getRowIndex();
			dataModel.setRowIndex(row);
		} else if (novoValor.compareTo(valorAnt) < 0) {
			FacesUtil.infoMessageSimples("Será realizado baixa de pagamento parcial no valor de "+ NumericUtil.moeda(novoValor));
		}
	}

	public void rowToggleSelect() {
		if (realizarBaixas != null && !realizarBaixas.isEmpty()) {
			permitirPagamento = true;
		} else {
			permitirPagamento = false;
		}
	}

	@Override
	public void salvar() {
		if (realizarBaixas != null && !realizarBaixas.isEmpty()) {
			pagamentoNeg.salvar(realizarBaixas);
			FacesUtil.infoMessageSimples("Baixa realizada");
			novo();
		} else {
			FacesUtil.infoMessageSimples("Selecione o item a ser realizado a baixa de pagamento");
		}
	}

	public void setRealizarBaixas(List<BaixaPagamento> realizarBaixas) {
		this.realizarBaixas = realizarBaixas;
	}

	public String styleAtraso(BaixaPagamento baixa){
		return baixa.getDebito().getSituacao().equals(SituacaoPagamentoEnum.ATRASO) ? "atraso" : "";
	}

	public void unChecked() {
		if (null == realizarBaixas || realizarBaixas.size() == 0) {
			permitirPagamento = false;
		}
	}
}
