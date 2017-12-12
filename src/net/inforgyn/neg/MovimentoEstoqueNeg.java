package net.inforgyn.neg;

import java.util.List;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import net.inforgyn.model.MovimentoEstoque;
import net.inforgyn.model.Produto;
import net.inforgyn.model.filterPesquisa.FilterMovimentoEstoque;
import net.inforgyn.qualifierCdi.ListProdutosMovimentoQ;
import net.inforgyn.repository.MovimentoEstoqueDao;

public class MovimentoEstoqueNeg {
	@Inject private MovimentoEstoqueDao dao;
	
	@Produces @ListProdutosMovimentoQ
	public List<Produto> listarProdutosMovimentoEstoque(FilterMovimentoEstoque filtro) {
		return dao.listarProdutosMovimentoEstoque(filtro);
	}
	
	public List<MovimentoEstoque> listarMovimentoEstoquePorProduto(Produto produto, FilterMovimentoEstoque filtro) {
		return dao.listarMovimentoEstoquePorProduto(produto, filtro);
	}
}
