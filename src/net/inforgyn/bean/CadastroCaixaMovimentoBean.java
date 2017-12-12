package net.inforgyn.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.inforgyn.constante.TipoLancamentoCaixaEnum;
import net.inforgyn.model.Caixa;
import net.inforgyn.model.CaixaMovimento;
import net.inforgyn.model.TipoRecebimento;
import net.inforgyn.neg.CaixaMovimentoNeg;
import net.inforgyn.neg.CaixaNeg;
import net.inforgyn.util.NumericUtil;
import net.inforgyn.util.faces.FacesUtil;

@Named
@ViewScoped
public class CadastroCaixaMovimentoBean implements CadastroBean, Serializable {

	private static final long serialVersionUID = 1L;

	private Caixa caixa;
	@Inject	private CaixaNeg caixaNeg;
	@Inject	private List<Caixa> caixasAberto;
	private CaixaMovimento movimento;
	private List<CaixaMovimento> movimentos;
	@Inject	private CaixaMovimentoNeg neg;
	private BigDecimal saldo;
	@Inject private List<TipoRecebimento> tiposRecebimento;
	
	private void calcularSaldo(){
		saldo = neg.calcularSaldo(movimentos);		
	}
	
	public void carregarMovimentos(){
		movimentos = neg.carregarMovimentos(caixa);
		calcularSaldo();
	}
		
	public Caixa getCaixa() {
		return caixa;
	}

	public List<Caixa> getCaixasAberto() {
		return caixasAberto;
	}
	
	public CaixaMovimento getMovimento() {
		return movimento;
	}
	
	public List<CaixaMovimento> getMovimentos() {
		return movimentos;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public String getSaldoMoeda(){
		return NumericUtil.moeda(saldo);
	}

	public List<TipoLancamentoCaixaEnum> getTipoLancamentos() {
		List<TipoLancamentoCaixaEnum> tipos = new ArrayList<TipoLancamentoCaixaEnum>();
		tipos.add(TipoLancamentoCaixaEnum.ENTRADA);
		tipos.add(TipoLancamentoCaixaEnum.SAIDA);

		return tipos;
	}

	public List<TipoRecebimento> getTiposRecebimento(){
		return tiposRecebimento;
	}

	@PostConstruct
	public void init() {
		novo();
		if(caixasAberto.size() > 0){
			caixa = caixasAberto.get(0);
			carregarMovimentos();
		}
	}

	@Override
	public void novo() {
		movimento = new CaixaMovimento();
		saldo = new BigDecimal(0);
	}

	@Override
	public void salvar() {
		movimento.setCaixa(caixa);
		movimento.setData(new Date());
		neg.salvar(movimento);
		novo();
		carregarMovimentos();
		FacesUtil.infoMessageSimples("Cadastrado com sucesso");
	}

	public void setCaixa(Caixa caixa) {
		this.caixa = caixa;
	}

	public void setMovimento(CaixaMovimento movimento) {
		this.movimento = movimento;
	}
}
