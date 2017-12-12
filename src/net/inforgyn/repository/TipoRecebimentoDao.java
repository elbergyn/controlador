package net.inforgyn.repository;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import net.inforgyn.model.TipoRecebimento;


public class TipoRecebimentoDao extends DaoModel{
	public TipoRecebimento recebimentoDinheiro() {
		Criteria criteria = persistence.getCriteria(TipoRecebimento.class);
		criteria.add(Restrictions.eq("descricao", "Dinheiro"));
		return (TipoRecebimento) persistence.pesquisarCriteriaUnique(criteria);
	}
}
