package net.inforgyn.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import net.inforgyn.constante.StatusVendaEnum;
import net.inforgyn.model.Orcamento;
import net.inforgyn.util.UsuarioSessaoUtil;

public class OrcamentoDao extends DaoModel{

	public List<Orcamento> orcamentosAbertos() {
		Criteria criteria = persistence.getCriteria(Orcamento.class);
		criteria.add(Restrictions.eq("status", StatusVendaEnum.ORCAMENTO));
		
		Disjunction or = Restrictions.disjunction();
		or.add(Restrictions.eq("usuario", UsuarioSessaoUtil.getUsuario()));
		or.add(Restrictions.eq("usuario", UsuarioSessaoUtil.getUsuario().getUsuarioPai()));
		criteria.add(or);
		
		criteria.addOrder(Order.asc("id"));
		
		return persistence.pesquisarListCriteria(criteria);
	}

	public Orcamento carregarLazy(Orcamento orcamento, String... relacoes) {
		return (Orcamento) persistence.carregarLazy(orcamento, relacoes);
	}
}
