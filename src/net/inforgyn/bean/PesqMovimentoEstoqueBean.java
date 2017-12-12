package net.inforgyn.bean;

import java.util.List;

import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.inforgyn.constante.TipoLancamentoEnum;
import net.inforgyn.model.MovimentoEstoque;
import net.inforgyn.model.Produto;
import net.inforgyn.model.filterPesquisa.FilterMovimentoEstoque;
import net.inforgyn.neg.MovimentoEstoqueNeg;
import net.inforgyn.neg.ProdutoNeg;
import net.inforgyn.qualifierCdi.ListProdutosMovimentoQ;

@Named
@ViewScoped
public class PesqMovimentoEstoqueBean implements PesquisaBean {

	@Inject private FilterMovimentoEstoque filtro;
	private List<MovimentoEstoque> movimentos;
	@Inject @ListProdutosMovimentoQ private List<Produto> produtos;
	@Inject private MovimentoEstoqueNeg neg;
	private Produto produto;
	@Inject ProdutoNeg produtoNeg;

	@Override
	public String cadastrar() {
		return "pretty:cadValorProduto";
	}

	public FilterMovimentoEstoque getFiltro() {
		return filtro;
	}

	public List<MovimentoEstoque> getMovimentos() {
		return movimentos;
	}

	@Override
	public String novo() {
		return "pretty:pesqMovimentoEstoque";
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	@Override
	public void pesquisar() {
		produtos = neg.listarProdutosMovimentoEstoque(filtro);
	}

	public void setFiltro(FilterMovimentoEstoque filtro) {
		this.filtro = filtro;
	}

	public Produto getProduto() {
		return produto;
	}
	
	public void rowSelect(){
		this.movimentos = neg.listarMovimentoEstoquePorProduto(produto, filtro);
	}
	
	public SelectItem[] getTipoLancamentos(){
		SelectItem[] itens = new SelectItem[TipoLancamentoEnum.values().length];
		int i = 0;
		for (TipoLancamentoEnum e : TipoLancamentoEnum.values()) {
			itens[i++] = new SelectItem(e, e.getDescricao());
		}
		return itens;
	}
	
	public void setProduto(Produto produto) {
		if(produto != null && produto.getId() != null)
		this.produto = produtoNeg.pesquisarPorId(produto.getId());
	}
}
