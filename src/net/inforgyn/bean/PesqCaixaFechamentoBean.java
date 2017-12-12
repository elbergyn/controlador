package net.inforgyn.bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.inforgyn.model.Caixa;
import net.inforgyn.model.CaixaFechamento;
import net.inforgyn.model.filterPesquisa.FilterCaixaFechamento;
import net.inforgyn.neg.CaixaFechamentoNeg;
import net.inforgyn.util.DataUtil;
import net.inforgyn.util.NumericUtil;

@Named
@ViewScoped
public class PesqCaixaFechamentoBean implements PesquisaBean {

	private Caixa caixa;
	private List<Caixa> caixas;
	private List<CaixaFechamento> fechamento;
	@Inject
	private FilterCaixaFechamento filtro;
	@Inject
	private CaixaFechamentoNeg neg;

	@Override
	public String cadastrar() {
		return "pretty:caixa";
	}

	public void carregarFechamento() {
		fechamento = neg.listarFechamentos(caixa);
	}

	public String calcularTotal() {
		BigDecimal total = new BigDecimal(0);
		if (fechamento != null) {
			for (CaixaFechamento f : fechamento) {
				total = total.add(f.getValor());
			}
		}
		return NumericUtil.moeda(total);
	}
	
	public String dataFechamento(){
		return fechamento != null ? DataUtil.getDataHoraString(fechamento.get(0).getData()) : "";
	}

	public Caixa getCaixa() {
		return caixa;
	}

	public List<Caixa> getCaixas() {
		return caixas;
	}

	public List<CaixaFechamento> getFechamento() {
		return fechamento;
	}

	public FilterCaixaFechamento getFiltro() {
		return filtro;
	}

	@PostConstruct
	public void init() {
	}

	@Override
	public String novo() {
		return "pretty:pesqFechamentoCaixa";
	}

	@Override
	public void pesquisar() {
		caixas = neg.pesquisar(filtro);
	}

	public void setCaixa(Caixa caixa) {
		this.caixa = caixa;
	}

	public void setCaixas(List<Caixa> caixas) {
		this.caixas = caixas;
	}

	public void setFiltro(FilterCaixaFechamento filtro) {
		this.filtro = filtro;
	}

}
