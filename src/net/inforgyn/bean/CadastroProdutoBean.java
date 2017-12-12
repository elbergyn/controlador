package net.inforgyn.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.LazyDataModel;

import net.inforgyn.model.Fornecedor;
import net.inforgyn.model.Produto;
import net.inforgyn.model.ValorProduto;
import net.inforgyn.neg.ProdutoNeg;
import net.inforgyn.solid.Money;
import net.inforgyn.util.faces.FacesUtil;

@Named
@ViewScoped
public class CadastroProdutoBean implements CadastroBean, Serializable {

	private static final long serialVersionUID = 1L;

	@Inject private List<Fornecedor> fornecedores;
	@Inject	private ProdutoNeg neg;
	@Inject	private Produto produto;
	@Inject private LazyDataModel<Produto> produtos;
	
	public void excluir() {
		neg.excluir(produto);
		FacesUtil.infoMessageSimples("Excluído: " + this.produto.getDescricao());
		novo();
	}

	public Produto getProduto() {
		return produto;
	}

	@PostConstruct
	public void init(){
		produto.setValor(new ValorProduto());
	}

	public List<Fornecedor> listarFornecedores(String filtro) {
		List<Fornecedor> filtrados = new ArrayList<Fornecedor>();
		for (Fornecedor d : fornecedores) {
			if (d.getDescricao().toLowerCase().startsWith(filtro.toLowerCase())) {
				filtrados.add(d);
			}
		}
		return filtrados;
	}

	public LazyDataModel<Produto> getProdutos() {
		return produtos;
	}

	@Override
	public void novo() {
		produto = new Produto();
	}

	@Override
	public void salvar() {
		if (produto.getId() == null || produto.getId() == 0) {
			produto = neg.salvar(produto);
			FacesUtil.infoMessageSimples("Produto cadastrado: "
					+ this.produto.getDescricao());
		} else {
			produto = neg.alterar(produto);
			FacesUtil.infoMessageSimples("Produto alterado: "
					+ this.produto.getDescricao());
		}

		novo();
	}
	
	public void calcularValor() {
		Money valor = Money.valueOf(produto.getValor().getValorCusto());
		Money percent = valor.multiplicar(produto.getValor().getLucroPercentual());
		produto.getValor().setValorVenda(valor.somar(percent).getBigDecimal());
	}

	public void calcularPercentual() {
		Double divisor = (produto.getValor().getValorVenda().doubleValue() / produto.getValor().getValorCusto().doubleValue()) - 1;
				
		produto.getValor().setLucroPercentual(divisor);
	}

	public void setProduto(Produto produto) {
		produto = neg.carregarProdutoCompleto(produto);
		produto.getEstoque().setQtde(0);
		this.produto = produto;
	}
}
