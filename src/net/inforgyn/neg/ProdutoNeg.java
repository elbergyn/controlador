package net.inforgyn.neg;

import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import net.inforgyn.constante.StatusProdutoEnum;
import net.inforgyn.model.Produto;
import net.inforgyn.model.ValorProduto;
import net.inforgyn.persistence.Transactional;
import net.inforgyn.qualifierCdi.ProdutoComboQ;
import net.inforgyn.repository.ProdutoDao;
import net.inforgyn.solid.Money;
import net.inforgyn.util.UsuarioSessaoUtil;

public class ProdutoNeg {
	
	@Inject private ProdutoDao dao;
	@Inject private EstoqueNeg estoqueNeg;
	@Inject private ValorProdutoNeg valorNeg;
	
	@Transactional
	public Produto salvar(Produto produto){
		produto.getEstoque().setUsuario(UsuarioSessaoUtil.getUsuario());
		produto.getEstoque().setProduto(produto);
		
		produto.getValor().setUsuario(UsuarioSessaoUtil.getUsuario());
		produto.getValor().setProduto(produto);
		 		
		produto = (Produto) dao.salvar(produto);
						
		estoqueNeg.gerarMovimentoEstoqueEntrada(produto.getEstoque());
		valorNeg.gerarHistorico(produto.getValor());
		
		return produto;
	}
	
	@Transactional
	public Produto alterar(Produto produto){
		produto.getValor().setProduto(produto);
		estoqueNeg.gerarMovimentoEstoqueEntrada(produto.getEstoque());
		valorNeg.gerarHistorico(produto.getValor());
		
		produto = (Produto) dao.alterar(produto);
		return produto;
	}
	
	public void excluir(Produto produto){
		//dao.excluir(produto);
		produto.setStatus(StatusProdutoEnum.INATIVO);
		dao.alterar(produto);
	}
	
	@Produces
	public List<Produto> listarProdutos(){
		return dao.listarProdutos();
	}
	
	@Produces @ProdutoComboQ
	@SessionScoped
	public List<Produto> listarCombo(){
		return dao.listarCombo();
	}
	
	public Produto pesquisarPorId(long id){
		return (Produto) dao.pesquisarPorId(Produto.class, id);
	}

	public Produto carregarProdutoCompleto(Produto produto) {
		return dao.carregarProdutoCompleto(produto);
	}
	
	public Produto carregarProdutoEstoque(Produto produto){
		return (Produto)dao.carregarLazy(produto, "estoque");
	}
	
	public void calcularValor(Produto produto) {
		Money valor = Money.valueOf(produto.getValor().getValorCusto());
		Money percent = valor.multiplicar(produto.getValor().getLucroPercentual());
		
		produto.getValor().setValorVenda(valor.somar(percent).getBigDecimal());
	}

	public void calcularPercentual(Produto produto) {
		Double divisor = (produto.getValor().getValorVenda().doubleValue() / produto.getValor().getValorCusto().doubleValue()) - 1;
				
		produto.getValor().setLucroPercentual(divisor);
	}

	public ValorProduto carregarValor(Produto produto) {
		return dao.carregarValor(produto);
	}

	public Produto pesquisarPorCodBarras(String codBarras) {
		return dao.pesquisarPorCodBarras(codBarras);
	}

	public List<Produto> pesquisar(Produto produto) {
		return dao.pesquisa(produto);
	}
	
}
