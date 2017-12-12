package net.inforgyn.repository;

import net.inforgyn.model.Estoque;
import net.inforgyn.model.Produto;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class EstoqueDao extends DaoModel{
	
	public Estoque pesquisarPorProduto(Produto produto) {
		Criteria criteria = persistence.getCriteria(Estoque.class);
		criteria.add(Restrictions.eq("produto", produto));
		
		return (Estoque) persistence.pesquisarCriteriaUnique(criteria);
	}

}
