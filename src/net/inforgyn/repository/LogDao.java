package net.inforgyn.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import net.inforgyn.impl.EntityPersistence;
import net.inforgyn.model.LogSisErro;
import net.inforgyn.model.filterPesquisa.FilterLog;
import net.inforgyn.persistence.JPAPersistenceUtil;

public class LogDao implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private JPAPersistenceUtil persistence;

	public void salvar(EntityPersistence entity) {
		persistence.salvar(entity);
	}

	@SuppressWarnings("unchecked")
	public List<LogSisErro> listar(FilterLog filtro) {
		Criteria criteria = persistence.getCriteria(LogSisErro.class);
		if (filtro != null) {
			if (filtro.getDataIni() != null) {
				criteria.add(Restrictions.gt("data", filtro.getDataIni()));
			}
			if (filtro.getDataFim() != null) {
				criteria.add(Restrictions.lt("data", filtro.getDataFim()));
			}
		}
		criteria.addOrder(Order.desc("data")); 
		return criteria.list();
	}

	public void excluir(List<LogSisErro> logs) {
		persistence.excluir(logs);
	}
}
