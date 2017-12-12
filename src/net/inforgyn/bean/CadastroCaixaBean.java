package net.inforgyn.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.inforgyn.constante.StatusCaixaEnum;
import net.inforgyn.model.Caixa;
import net.inforgyn.model.CaixaFechamento;
import net.inforgyn.neg.CaixaNeg;
import net.inforgyn.neg.FechamentoNeg;
import net.inforgyn.util.faces.FacesUtil;

@Named
@ViewScoped
public class CadastroCaixaBean implements CadastroBean, Serializable {

	private static final long serialVersionUID = 1L;
	@Inject	private Caixa caixa;
	@Inject	private List<Caixa> caixasAberto;
	@Inject	private CaixaNeg neg;
	@Inject private FechamentoNeg fechamentoNeg;
	private List<CaixaFechamento> fechamentos;
	
	public CadastroCaixaBean() {
		if(FacesUtil.isNotPostBack()){
			
		}
	}

	public Caixa getCaixa() {
		return caixa;
	}

	public List<Caixa> getCaixasAberto() {
		return caixasAberto;
	}
	
	public void caixaParcial(){
		neg.caixaParcial(caixasAberto);
	}
	
	public void calcularCaixa(){
		fechamentos = neg.calcularFechamento(caixa);
	}
	
	public List<CaixaFechamento> getFechamento(){
		return fechamentos;
	}
	
	public void fecharCaixa() throws Exception{
		caixa.setStatus(StatusCaixaEnum.FECHADO);
		
		for(CaixaFechamento f : fechamentos){
			f.setCaixa(caixa);
		}
		
		fechamentoNeg.salvar(fechamentos);
		neg.alterar(caixa);
		
		caixasAberto = neg.caixasAbertos();
		
		FacesUtil.infoMessageSimples("Realizado fechamento de caixa");
		novo();
	}

	@PostConstruct
	public void init() {
		caixaParcial();
	}

	@Override
	public void novo() {
		caixa = new Caixa();
		
	}

	@Override
	public void salvar() {
		boolean existe = neg.possuiCaixa(caixa.getData());
		if (existe) {
			FacesUtil.alertMessageSimples("Já possui caixa aberto para a data "+caixa.getDataString());
		} else {
			caixa = neg.salvar(caixa);
			FacesUtil.infoMessageSimples("Realizado abertura de caixa");
			caixasAberto.add(caixa);
			caixaParcial();
		}
	}

	public void setCaixa(Caixa caixa) {
		this.caixa = caixa;
	}
}
