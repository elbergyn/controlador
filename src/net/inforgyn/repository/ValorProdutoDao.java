package net.inforgyn.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.inforgyn.constante.StatusProdutoEnum;
import net.inforgyn.model.ValorProduto;
import net.inforgyn.util.UsuarioSessaoUtil;

import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

public class ValorProdutoDao extends DaoModel{

	public List<ValorProduto> listrarValores(){
		StringBuilder sb = new StringBuilder();
		sb.append("from ").append(ValorProduto.class.getName());
		sb.append(" valor ");
		sb.append(" join fetch valor.produto produto");
		sb.append(" where produto.status = :status ");
		sb.append(" and (valor.usuario = :usuario or valor.usuario = :pai) ");
		sb.append(" order by produto.descricao, produto.id");
		
		Map<String, Object> filtro = new HashMap<String, Object>();
		filtro.put("usuario", UsuarioSessaoUtil.getUsuario());
		filtro.put("pai", UsuarioSessaoUtil.getUsuario().getUsuarioPai());
		filtro.put("status", StatusProdutoEnum.ATIVO);
		
		return persistence.executarPesquisaHql(sb.toString(), filtro, ValorProduto.class);
	}

	public ValorProduto pesquisarPorIdProduto(Long id) {
		Criteria criteria = persistence.getCriteria(ValorProduto.class);
		criteria.createAlias("produto", "produto");
		criteria.add(Restrictions.eq("produto.id", id));
		criteria.add(Restrictions.eq("produto.status", StatusProdutoEnum.ATIVO));
		
		Disjunction or = Restrictions.disjunction();
		or.add(Restrictions.eq("usuario", UsuarioSessaoUtil.getUsuario()));
		or.add(Restrictions.eq("usuario", UsuarioSessaoUtil.getUsuario().getUsuarioPai()));
		
		criteria.add(or);
		
		return (ValorProduto) persistence.pesquisarCriteriaUnique(criteria);
	}
}
