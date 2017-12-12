package net.inforgyn.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import net.inforgyn.model.Venda;
import net.inforgyn.model.filterPesquisa.FilterVenda;


public class VendaDao extends DaoModel {

	@SuppressWarnings("unchecked")
	public List<Venda> pesquisar(FilterVenda filtro) {
		
		Criteria criteria = persistence.getCriteria(Venda.class);
		if(filtro.getInicio() != null && filtro.getFim() != null){
			criteria.add(Restrictions.between("data", filtro.getInicio(), filtro.getFim()));
		}else  if(filtro.getInicio() != null){
			criteria.add(Restrictions.ge("data", filtro.getInicio()));
		}else if(filtro.getFim() != null){
			criteria.add(Restrictions.le("data", filtro.getFim()));
		}
		
		return criteria.list();
	}
}
