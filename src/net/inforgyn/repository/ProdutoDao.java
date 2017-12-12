package net.inforgyn.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.inforgyn.constante.StatusProdutoEnum;
import net.inforgyn.model.Produto;
import net.inforgyn.model.ValorProduto;
import net.inforgyn.util.UsuarioSessaoUtil;

import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class ProdutoDao extends DaoModel{
	
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public List<Produto> listarProdutos(){		
		Criteria criteria = persistence.getCriteria(Produto.class);
		
		criteria.add(Restrictions.eq("status", StatusProdutoEnum.ATIVO));
		
		Disjunction or = Restrictions.disjunction();
		or.add(Restrictions.eq("usuario", UsuarioSessaoUtil.getUsuario()));
		or.add(Restrictions.eq("usuario", UsuarioSessaoUtil.getUsuario().getUsuarioPai()));
		
		criteria.add(or);
		
		criteria.addOrder(Order.asc("descricao"));
		
		return (List<Produto>)persistence.pesquisarCriteriaList(criteria);
	}

	public Produto carregarProdutoCompleto(Produto produto) {
		return (Produto) persistence.carregarLazy(produto, "estoque","fornecedor","valor");
	}

	public ValorProduto carregarValor(Produto produto) {
		StringBuilder sb = new StringBuilder();
		sb.append("from ").append(ValorProduto.class.getName());
		sb.append(" where produto.id = :id");
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("id", produto.getId());
		
		return (ValorProduto) persistence.pesquisarHqlUnique(sb.toString(), parametros);
	}

	public  List<Produto> listarCombo() {
		StringBuilder sb = new StringBuilder();
		sb.append("select new Produto(id, descricao, codBarras) from ").append(Produto.class.getName());
		sb.append(" where status = :status");
		sb.append(" order by descricao");
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("status", StatusProdutoEnum.ATIVO);
		
		return persistence.pesquisaHql(sb.toString(), parametros);
	}
	
	public Produto produtoBasico(Long id) {
		StringBuilder sb = new StringBuilder();
		sb.append("select new Produto(id, descricao, codBarras) from ").append(Produto.class.getName());
		sb.append(" where id = :id");
		sb.append(" and status = :status");
		sb.append(" order by descricao");
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("id", id);
		parametros.put("status", StatusProdutoEnum.ATIVO);
		
		return (Produto) persistence.pesquisarHqlUnique(sb.toString(), parametros);
	}

	public Produto pesquisarPorCodBarras(String codBarras) {
		StringBuilder sb = new StringBuilder();
		sb.append("select new Produto(id, descricao, codBarras) from ").append(Produto.class.getName());
		sb.append(" where codBarras = :cod");
		sb.append(" and status = :status");
		sb.append(" order by descricao");
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("cod", codBarras);
		parametros.put("status", StatusProdutoEnum.ATIVO);
		return (Produto) persistence.pesquisarHqlUnique(sb.toString(), parametros);
	}

	public List<Produto> pesquisa(Produto produto) {
		Criteria criteria = persistence.getCriteria(Produto.class);
		criteria.add(Restrictions.ilike("descricao", produto.getDescricao()));
		criteria.add(Restrictions.eq("codBarras", produto.getCodBarras()));
		criteria.add(Restrictions.eq("status", StatusProdutoEnum.ATIVO));
		return criteria.list();
	}
}
