package net.inforgyn.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.inforgyn.constante.SituacaoPagamentoEnum;
import net.inforgyn.model.BaixaRecebimento;
import net.inforgyn.model.Devedor;
import net.inforgyn.neg.BaixaRecebimentoNeg;
import net.inforgyn.util.DataUtil;
import net.inforgyn.util.NumericUtil;
import net.inforgyn.util.faces.FacesUtil;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;

@Named
@ViewScoped
public class BaixaRecebimentoBean implements CadastroBean, Serializable{

	private static final long serialVersionUID = 1L;
	
	private List<BaixaRecebimento> baixas;	
	@Inject private List<Devedor> devedores;
	private Integer filtroAno;
	private List<Devedor> filtroDevedores;
	private Integer filtroMes;
	private Boolean permitirPagamento = false;
	private List<BaixaRecebimento> realizarBaixas;
	@Inject private BaixaRecebimentoNeg recebimentoNeg;
	
	public void carregarCreditos(Map<String, Object> filtro){
		baixas = recebimentoNeg.gerarBaixaRecebimento(filtro);
	}
	
	public void checked(){
		if(null != realizarBaixas && !realizarBaixas.isEmpty()){
			permitirPagamento = true;
		}
	}
	
	public void filtrar(){
		Map<String, Object> filtro = new HashMap<String, Object>();
		filtro.put("mes", filtroMes);
		filtro.put("devedores", filtroDevedores);
		filtro.put("ano", filtroAno);
		carregarCreditos(filtro);
	}

	public List<Integer> getAnos(){
		List<Integer> anos = recebimentoNeg.listarAnos();
		if(anos.size() == 0){
			anos.add(DataUtil.getAno());
		}
		return anos;
	}

	public List<BaixaRecebimento> getBaixas(){
    	return baixas;
    }
	
    public List<Devedor> getDevedores(){
    	return devedores;
    }

	public Integer getFiltroAno() {
		return filtroAno;
	}

	public List<Devedor> getFiltroDevedores() {
		return filtroDevedores;
	}
	
    public Integer getFiltroMes() {
		return filtroMes;
	}	

	public SelectItem[] getMeses(){
    	return DataUtil.getMeses();
    }

	public Boolean getPermitirPagamento(){
		return !permitirPagamento;
	}
    
    public List<BaixaRecebimento> getRealizarBaixas() {
		return realizarBaixas;
	}
    
    public String getValorReceber(){
		BigDecimal valor = new BigDecimal(0);
		if(null != baixas && !baixas.isEmpty()){
			for(BaixaRecebimento baixa : baixas){
				valor = valor.add(baixa.getValor());
			}
		}
		return NumericUtil.moeda(valor);
	}
    
    public String getValorTotal(){
		BigDecimal valor = new BigDecimal(0);
		if(null != baixas && !baixas.isEmpty()){
			for(BaixaRecebimento baixa : baixas){
				valor = valor.add(baixa.getCredito().getValor());
			}
		}
		return NumericUtil.moeda(valor);
	}
    
    @PostConstruct
    public void init() {
		carregarCreditos(null);
    }

	public void limpar(){
		realizarBaixas = new ArrayList<BaixaRecebimento>();
		filtroDevedores = null;
		filtroMes = null;
		permitirPagamento = false;
		carregarCreditos(null);
	}
	
	@Override
	public void novo() {
		carregarCreditos(null);
		realizarBaixas = new ArrayList<BaixaRecebimento>();
	}
	
	public void onCellEdit(CellEditEvent event) {
		BigDecimal valorAnt = (BigDecimal) event.getOldValue();
		BigDecimal novoValor = (BigDecimal) event.getNewValue();
		
		DataTable dataModel = (DataTable) event.getSource();
		BaixaRecebimento baixa = (BaixaRecebimento) dataModel.getRowData();
        
        if(novoValor.compareTo(valorAnt) > 0){
        	baixa.setValor(valorAnt);        
        	FacesUtil.alertMessageSimples("Valor de recebimento não deve ser maior que valor disponibilizado para recebimento");
        	Integer row = event.getRowIndex();
        	dataModel.setRowIndex(row);
        }else if(novoValor.compareTo(valorAnt) < 0) {
            FacesUtil.infoMessageSimples("Será realizado baixa de recebimento parcial no valor de "+NumericUtil.moeda(novoValor));
        }
    }
	
	public void rowToggleSelect(){
		if(realizarBaixas != null && !realizarBaixas.isEmpty()){
			permitirPagamento = true;
		}else{
			permitirPagamento = false;
		}
	}
	
	@Override
    public void salvar(){
    	if(realizarBaixas != null && !realizarBaixas.isEmpty()){
			recebimentoNeg.salvar(realizarBaixas);
			FacesUtil.infoMessageSimples("Baixa realizada");
			novo();
		}else{
			FacesUtil.infoMessageSimples("Selecione o item a ser realizado a baixa de recebimento");
		}
    }
	
	public void setFiltroAno(Integer filtroAno) {
		this.filtroAno = filtroAno;
	}
	
	public void setFiltroDevedores(List<Devedor> filtroDevedores) {
		this.filtroDevedores = filtroDevedores;
	}
    
    public void setFiltroMes(Integer filtroMes) {
		this.filtroMes = filtroMes;
	}

	public void setRealizarBaixas(List<BaixaRecebimento> realizarBaixas) {
		this.realizarBaixas = realizarBaixas;
	}

	public String styleAtraso(BaixaRecebimento baixa){
		return baixa.getCredito().getSituacao().equals(SituacaoPagamentoEnum.ATRASO) ? "atraso" : "";
	}
    
    public void unChecked(){
		if(null == realizarBaixas || realizarBaixas.size() == 0){
			permitirPagamento = false;
		}
	}
}
