package net.inforgyn.bean;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.inforgyn.model.Venda;
import net.inforgyn.model.filterPesquisa.FilterVenda;
import net.inforgyn.neg.VendaNeg;
import net.inforgyn.util.NumericUtil;

@Named
@ViewScoped
public class PesqVendasBean implements PesquisaBean {

	private List<Venda> vendas;
	@Inject	private FilterVenda filtro;
	private BigDecimal total;

	@Inject
	private VendaNeg neg;

	@Override
	public void pesquisar() {
		vendas = neg.pesquisar(filtro);
		calcularTotal();
	}

	public String getTotal() {
		return NumericUtil.moeda(total);
	}

	@PostConstruct
	public void init() {
		calcularTotal();
	}

	private void calcularTotal() {
		total = new BigDecimal(0);
		if (vendas != null) {
			for (Venda v : vendas) {
				total = total.add(v.getValorTotal());
			}
		}
	}

	@Override
	public String cadastrar() {
		return "pretty:cadOrcamento";
	}

	@Override
	public String novo() {
		return "pretty:pesqVendas";
	}

	public List<Venda> getVendas() {
		return vendas;
	}

	public FilterVenda getFiltro() {
		return filtro;
	}

	public void setFiltro(FilterVenda filtro) {
		this.filtro = filtro;
	}
}
