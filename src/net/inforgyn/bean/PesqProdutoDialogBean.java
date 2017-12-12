package net.inforgyn.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import net.inforgyn.model.Produto;
import net.inforgyn.neg.ProdutoNeg;

@ViewScoped
@Named
public class PesqProdutoDialogBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Produto produto;
	private ProdutoNeg neg;
	private List<Produto> produtos;
	
	public PesqProdutoDialogBean() {
		
	}
	
	public void pesquisar(){
		produtos = neg.pesquisar(produto);
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public List<Produto> getProdutos() {
		return produtos;
	}
}
