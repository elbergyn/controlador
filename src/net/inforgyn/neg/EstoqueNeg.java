package net.inforgyn.neg;

import java.util.List;

import javax.enterprise.inject.Produces;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import net.inforgyn.constante.TipoLancamentoEnum;
import net.inforgyn.model.Estoque;
import net.inforgyn.model.MovimentoEstoque;
import net.inforgyn.model.Produto;
import net.inforgyn.persistence.Transactional;
import net.inforgyn.repository.EstoqueDao;
import net.inforgyn.solid.MovimentoEstoqueEntrada;
import net.inforgyn.solid.MovimentoEstoqueSaida;
import net.inforgyn.util.UsuarioSessaoUtil;

public class EstoqueNeg {
	@Inject private EstoqueDao dao;
	
	public Estoque pesquisarPorProduto(Produto produto){
		return dao.pesquisarPorProduto(produto);
	}
	
	public SelectItem[] getTiposLancamento(){
		SelectItem[] itens = new SelectItem[TipoLancamentoEnum.values().length];
		int i = 0;
		for (TipoLancamentoEnum e : TipoLancamentoEnum.values()) {
			itens[i++] = new SelectItem(e, e.getDescricao());
		}
		return itens;
	}
	
	@Transactional
	public void gerarMovimentoEstoqueSaida(Estoque estoque){
		MovimentoEstoque movimento = new MovimentoEstoqueSaida(estoque).movimentar();
		if(estoque.getId() != null){
			Estoque estoqueBanco = pesquisarPorProduto(estoque.getProduto());
			if(estoque.getQtde() == 0){
				estoque.setQtde(estoqueBanco.getQtde());
			}else{
				estoque.setQtde(estoqueBanco.getQtde()-estoque.getQtde());
				dao.salvar(movimento);
			}
			dao.alterar(estoque);
		}else{
			dao.salvar(estoque);
			dao.salvar(movimento);
		}
	}
	
	@Transactional
	public void gerarMovimentoEstoqueEntrada(Estoque estoque){
		MovimentoEstoque movimento = new MovimentoEstoqueEntrada(estoque).movimentar();
		if(estoque.getId() != null){
			Estoque estoqueBanco = pesquisarPorProduto(estoque.getProduto());
			if(estoque.getQtde() == 0){
				estoque.setQtde(estoqueBanco.getQtde());
			}else{
				estoque.setQtde(estoque.getQtde()+estoqueBanco.getQtde());
				dao.salvar(movimento);
			}
			dao.alterar(estoque);
		}else{
			dao.salvar(estoque);
			dao.salvar(movimento);
		}
	}
	
	@Produces
	public List<Estoque> listarEstoques(){
		return dao.listarPorUsuarioComPai(Estoque.class, UsuarioSessaoUtil.getUsuario());
	}

	public Estoque pesquisarPorIdProduto(Long id) {
		return pesquisarPorProduto(new Produto(id));
	}
}
