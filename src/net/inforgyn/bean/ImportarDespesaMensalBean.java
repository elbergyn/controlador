package net.inforgyn.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.inforgyn.adapter.ContaPagarAdapter;
import net.inforgyn.model.ContaPagar;
import net.inforgyn.model.DespesaMensal;
import net.inforgyn.neg.ContaPagarNeg;
import net.inforgyn.neg.DespesasMensaisNeg;
import net.inforgyn.util.DataUtil;
import net.inforgyn.util.NumericUtil;
import net.inforgyn.util.UsuarioSessaoUtil;
import net.inforgyn.util.faces.FacesUtil;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;

@Named
@ViewScoped
public class ImportarDespesaMensalBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer ano;
	@Inject private ContaPagarNeg contaNeg;
	@Inject private DespesasMensaisNeg despesaNeg;
	private List<DespesaMensal> despesas;
	private List<DespesaMensal> despesasSelecionadas = new ArrayList<DespesaMensal>();
	private boolean importarContas = false;
	private Integer mes;
	
	
	public void checked() {
		if (null != despesasSelecionadas && !despesasSelecionadas.isEmpty()) {
			importarContas = true;
		}
	}
	
	public void gerarContas(){
		List<ContaPagar> contasGeradas = new ArrayList<ContaPagar>();
		StringBuilder alerta = new StringBuilder();
		for(DespesaMensal despesa : despesasSelecionadas){
			ContaPagar conta = ContaPagarAdapter.adapter(despesa, mes-1, ano);
			contaNeg.calcularParcelamento(conta);
			conta.setUsuario(UsuarioSessaoUtil.getUsuario());
			boolean existe = contaNeg.validarExistencia(conta);
			if(!existe){
				contasGeradas.add(conta);
			}else{
				if(alerta.length() == 0){
					alerta.append("<b>Já cadastro para seguintes despesas: </b><br/>");
				}
				alerta.append("<br/>");
				alerta.append(despesa.getDescricao()+" no mês "+mes+"/"+ano);
			}
		}
		if(!contasGeradas.isEmpty()){
			contaNeg.salvar(contasGeradas);
			FacesUtil.infoMessageSimples("Gerado contas selecionadas");
		}
		
		despesasSelecionadas = new ArrayList<DespesaMensal>();
		if(alerta.length() > 0){
			RequestContext.getCurrentInstance().showMessageInDialog(new
					FacesMessage(FacesMessage.SEVERITY_INFO,
					"Importação de despesas", alerta.toString()));
		}
	}
	
	public Integer getAno(){
		return ano;
	}
	
	public List<Integer> anosSelect(){
		List<Integer> anos = new ArrayList<Integer>();
		anos.add(DataUtil.getAno());
		anos.add(DataUtil.getAno()+1);
		return anos;
	}
	
	public List<DespesaMensal> getDespesas() {
		return despesas;
	}
	
	public List<DespesaMensal> getDespesasSelecionadas() {
		return despesasSelecionadas;
	}
	
	public Integer getMes() {
		mes = DataUtil.getMes()+1;
		return mes;
	}
	
	@PostConstruct
	public void init(){
		despesas = despesaNeg.listarDespesas();
	}
	
	public boolean isImportarContas() {
		return !importarContas;
	}

	public SelectItem[] meses(){
		return DataUtil.getMeses();
	}
	
	public void onCellEdit(CellEditEvent event) {
		Object obj = event.getNewValue();
		DataTable dataModel = (DataTable) event.getSource();
		DespesaMensal despesa = (DespesaMensal) dataModel.getRowData();

		if(obj instanceof BigDecimal){
			BigDecimal novoValor = (BigDecimal) obj;
			despesa.setValor(novoValor);
			FacesUtil.alertMessageSimples("Será realizado o lançamento da conta " +despesa.getDescricao()+ " no valor de "+NumericUtil.moeda(novoValor));
		}else{
			Integer novoValor = (Integer) obj;
			despesa.setDiaVencimento(novoValor);
			FacesUtil.alertMessageSimples("Será gerado vencimento da conta " +despesa.getDescricao()+ " no dia "+novoValor);
		}
		
	}

	public void rowToggleSelect() {
		if (despesasSelecionadas != null && !despesasSelecionadas.isEmpty()) {
			importarContas = true;
		} else {
			importarContas = false;
		}
	}
	
	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public void setDespesasSelecionadas(List<DespesaMensal> despesasSelecionadas) {
		this.despesasSelecionadas = despesasSelecionadas;
	}

	public void setImportarContas(boolean importarContas) {
		this.importarContas = importarContas;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	public void unChecked() {
		if (null == despesasSelecionadas || despesasSelecionadas.size() == 0) {
			importarContas = false;
		}
	}
}
