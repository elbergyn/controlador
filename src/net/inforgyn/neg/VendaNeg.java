package net.inforgyn.neg;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import net.inforgyn.constante.StatusVendaEnum;
import net.inforgyn.model.Estoque;
import net.inforgyn.model.ItemVenda;
import net.inforgyn.model.Venda;
import net.inforgyn.model.filterPesquisa.FilterVenda;
import net.inforgyn.persistence.Transactional;
import net.inforgyn.repository.VendaDao;

@RequestScoped
public class VendaNeg {
	
	@Inject private VendaDao dao;
	@Inject private EstoqueNeg estoqueNeg;

	@Transactional
	public Venda salvar(Venda venda) { 
		for(ItemVenda item : venda.getOrcamento().getItensVenda()){
			Estoque estoque = estoqueNeg.pesquisarPorProduto(item.getProduto());
			Estoque novo = estoque.clone();
			novo.setQtde(item.getQtde());
			estoqueNeg.gerarMovimentoEstoqueSaida(novo);
		}
		venda.getOrcamento().setStatus(StatusVendaEnum.CONCLUIDO);
		dao.alterar(venda.getOrcamento());
		return (Venda) dao.salvar(venda);
	}

	public List<Venda> pesquisar(FilterVenda filtro) {
		return dao.pesquisar(filtro);
	}

}
