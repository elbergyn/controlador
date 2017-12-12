package net.inforgyn.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.inforgyn.model.HistoricoValorProduto;
import net.inforgyn.model.filterPesquisa.FilterHistoricoValor;
import net.inforgyn.neg.HistoricoValorProdutoNeg;

@Named
@ViewScoped
public class PesqHistoricoValorBean implements PesquisaBean, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject private FilterHistoricoValor filtro;
	@Inject private HistoricoValorProdutoNeg neg;
	private List<HistoricoValorProduto> historicos;

	@Override
	public String cadastrar() {
		return "pretty:cadValorProduto";
	}

	public FilterHistoricoValor getFiltro() {
		return filtro;
	}



	@Override
	public String novo() {
		return "pretty:pesqHistoricoValor";
	}

	@Override
	public void pesquisar() {
		historicos = neg.listarProdutosComHistorico(filtro);
	}

	public void setFiltro(FilterHistoricoValor filtro) {
		this.filtro = filtro;
	}

	public List<HistoricoValorProduto> getHistoricos() {
		return historicos;
	}

	public void setHistoricos(List<HistoricoValorProduto> historicos) {
		this.historicos = historicos;
	}
}
