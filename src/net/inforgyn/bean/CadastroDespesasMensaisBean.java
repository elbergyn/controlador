package net.inforgyn.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.inforgyn.model.DespesaMensal;
import net.inforgyn.neg.DespesasMensaisNeg;
import net.inforgyn.util.faces.FacesUtil;

@Named
@ViewScoped
public class CadastroDespesasMensaisBean implements CadastroBean, Serializable{

	private static final long serialVersionUID = 1L;
	@Inject private DespesasMensaisNeg despesaNeg;
	@Inject	private DespesaMensal despesa;
	@Inject List<DespesaMensal> despesas;

	@Override
	public void novo() {
		despesa = new DespesaMensal();
	}

	@Override
	public void salvar() {
		if(despesa.getId() == null){
			despesa = despesaNeg.salvar(despesa);
			despesas.add(despesa);
			FacesUtil.infoMessageSimples("Despesa cadastrada: "+this.despesa.getDescricao());
		}else {
			despesa = despesaNeg.alterar(despesa);
			FacesUtil.infoMessageSimples("Despesa alterada: "+this.despesa.getDescricao());
		}
		novo();
	}

	@PostConstruct
	public void init(){
	}
	
	public void excluir(){
		despesaNeg.excluir(despesa);
		despesas.remove(despesa);
		FacesUtil.infoMessageSimples("Despesa removida");
		novo();
	}

	public DespesaMensal getDespesa() {
		return despesa;
	}

	public void setDespesa(DespesaMensal despesa) {
		this.despesa = despesa;
	}
	
	public List<DespesaMensal> listarDespesas(){
		return despesas;
	}
}
