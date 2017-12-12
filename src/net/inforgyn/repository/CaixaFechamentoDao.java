package net.inforgyn.repository;

import java.util.List;

import net.inforgyn.constante.StatusCaixaEnum;
import net.inforgyn.model.Caixa;
import net.inforgyn.model.CaixaFechamento;
import net.inforgyn.model.filterPesquisa.FilterCaixaFechamento;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class CaixaFechamentoDao extends DaoModel{

	public List<Caixa> pesquisar(FilterCaixaFechamento filtro) {
		
		Criteria criteria = persistence.getCriteria(Caixa.class);
		
		if(filtro.getInicio() != null){
			criteria.add(Restrictions.ge("data", filtro.getInicio()));
		}
		
		if(filtro.getFim() != null){
			criteria.add(Restrictions.le("data", filtro.getFim()));
		}
		
		criteria.add(Restrictions.eq("status", StatusCaixaEnum.FECHADO));
		criteria.addOrder(Order.asc("data"));
		
		return persistence.pesquisarListCriteria(criteria);
	}

	public List<CaixaFechamento> listarFechamentos(Caixa caixa) {
		Criteria criteria = persistence.getCriteria(CaixaFechamento.class);
		criteria.add(Restrictions.eq("caixa", caixa));
		return persistence.pesquisarListCriteria(criteria);
	}
}
