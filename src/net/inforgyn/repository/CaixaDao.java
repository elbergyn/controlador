package net.inforgyn.repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import net.inforgyn.constante.StatusCaixaEnum;
import net.inforgyn.exception.InfoException;
import net.inforgyn.model.Caixa;
import net.inforgyn.model.CaixaFechamento;
import net.inforgyn.model.CaixaMovimento;
import net.inforgyn.model.Usuario;
import net.inforgyn.util.UsuarioSessaoUtil;

public class CaixaDao extends DaoModel {

	@SuppressWarnings("unchecked")
	public List<Caixa> caixasAbertos(Usuario usuario) {
		Criteria criteria = persistence.getCriteria(Caixa.class);
		criteria.add(Restrictions.eq("status", StatusCaixaEnum.ABERTO));
		criteria.addOrder(Order.asc("data"));
		return persistence.pesquisarListCriteria(criteria);
	}

	public Caixa carregarMovimentos(Caixa caixa) {
		caixa = (Caixa) persistence.carregarLazy(caixa, "movimentos");
		return caixa;
	}

	public boolean possuiCaixa(Date data) {
		Criteria criteria = persistence.getCriteria(Caixa.class);
		criteria.add(Restrictions.eq("data", data));
		criteria.add(Restrictions.eq("usuario", UsuarioSessaoUtil.getUsuario()));
		Caixa cx = (Caixa) persistence.pesquisarCriteriaUnique(criteria);
		if(cx != null && cx.getStatus().equals(StatusCaixaEnum.FECHADO)){
			throw new InfoException("Este caixa já foi fechado");
		}
		return cx != null;
	}
	
	public List<CaixaFechamento> calcularFechamento(Caixa caixa) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("select new ").append(CaixaFechamento.class.getName());
		sb.append(" (m.tipoRecebimento.id, m.tipoRecebimento.descricao, sum(m.valor)) ");
		sb.append("from ").append(CaixaMovimento.class.getName()).append(" m ");
		sb.append("where m.caixa = :caixa ");
		sb.append("group by m.tipoRecebimento.id, m.tipoRecebimento.descricao");
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("caixa", caixa);
				
		return persistence.executarPesquisaHql(sb.toString(), parametros, CaixaFechamento.class);
	}
}