package net.inforgyn.repository;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.inject.Inject;

import net.inforgyn.dataPaginacao.FiltroPaginacao;
import net.inforgyn.impl.EntityPersistence;
import net.inforgyn.model.Usuario;
import net.inforgyn.persistence.JPAPersistenceUtil;
import net.inforgyn.util.UsuarioSessaoUtil;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public abstract class DaoModel {
	
	@Inject protected JPAPersistenceUtil persistence;
		
	public EntityPersistence salvar(EntityPersistence entity) {
		/*if(entity.getUsuario() == null){
			entity.setUsuario(UsuarioSessaoUtil.getUsuario());
		}*/
		return persistence.salvar(entity);
	}

	public EntityPersistence alterar(EntityPersistence entity) {
		entity.setUsuario(UsuarioSessaoUtil.getUsuario());
		return persistence.atualizar(entity);
	}

	public void excluir(EntityPersistence entity) {
		persistence.excluir(entity);
	}
	
	public void excluir(EntityPersistence entity, String... lazy) {
		persistence.excluir(entity, lazy);
	}
	
	public <T> List<T> listarPorUsuarioComPai(Class clazz, Usuario usuario) {
		return persistence.listarPorUsuarioComPai(clazz, usuario);
	}
	
	public <T> List<T> listarPorUsuario(Class clazz, Usuario usuario) {
		return persistence.listarPorUsuario(clazz, usuario);
	}
	
	public <T> void salvar(List<T> entitys) {
		persistence.salvar(entitys);
	}

	public EntityPersistence pesquisarPorId(Class classe, Long id) {
		return persistence.pesquisarPorId(classe, id);
	}
	
	public EntityPersistence carregarLazy(EntityPersistence entity, String... relacoes){
		return persistence.carregarLazy(entity, relacoes);
	}
	
	public int totalPaginacao(FiltroPaginacao filtro, Class clazz){
		Criteria criteria = persistence.getCriteria(clazz);
		Entry<String, Object> mapEntry = null;
		for (Iterator iterator = filtro.getFilters().entrySet().iterator(); iterator
				.hasNext();) {
			mapEntry = (Entry) iterator.next();
			criteria.add(Restrictions.ilike((String) mapEntry.getKey(), (String) mapEntry.getValue(), MatchMode.START));			
		}
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	public int totalPaginacao(Criteria criteria, FiltroPaginacao filtro){
		Entry<String, Object> mapEntry = null;
		for (Iterator iterator = filtro.getFilters().entrySet().iterator(); iterator
				.hasNext();) {
			mapEntry = (Entry) iterator.next();
			criteria.add(Restrictions.ilike((String) mapEntry.getKey(), (String) mapEntry.getValue(), MatchMode.START));			
		}
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	public <T> List<T> pesquisaPaginacao(FiltroPaginacao filtro, Class clazz){
		Criteria criteria = persistence.getCriteria(clazz);
		criteria.setFirstResult(filtro.getFirst());
		criteria.setMaxResults(filtro.getPageSize());
		
		if(filtro.isOrder() && filtro.getSortField() != null){
			criteria.addOrder(Order.asc(filtro.getSortField()));
		}else if(filtro.getSortField() != null){
			criteria.addOrder(Order.desc(filtro.getSortField()));
		}
		
		Entry<String, Object> mapEntry = null;

		for (Iterator iterator = filtro.getFilters().entrySet().iterator(); iterator
				.hasNext();) {
			mapEntry = (Entry) iterator.next();
			criteria.add(Restrictions.ilike((String) mapEntry.getKey(), (String) mapEntry.getValue(), MatchMode.START));			
		}
		
		return persistence.pesquisarListCriteria(criteria);
	}
	
	public <T> List<T> pesquisaPaginacao(Criteria criteria, FiltroPaginacao filtro){
		criteria.setFirstResult(filtro.getFirst());
		criteria.setMaxResults(filtro.getPageSize());
		
		if(filtro.isOrder() && filtro.getSortField() != null){
			criteria.addOrder(Order.asc(filtro.getSortField()));
		}else if(filtro.getSortField() != null){
			criteria.addOrder(Order.desc(filtro.getSortField()));
		}
		
		Entry<String, Object> mapEntry = null;

		for (Iterator iterator = filtro.getFilters().entrySet().iterator(); iterator
				.hasNext();) {
			mapEntry = (Entry) iterator.next();
			criteria.add(Restrictions.ilike((String) mapEntry.getKey(), (String) mapEntry.getValue(), MatchMode.START));			
		}
		
		return persistence.pesquisarListCriteria(criteria);
	}
	
	public Criteria getCriteria(Class clazz){
		return persistence.getCriteria(clazz);
	}
	
	/*private void buscaComUsuarioPai(Class classe, Long id){
		Criteria criteria = persistence.getCriteria(classe);
		criteria.add(Restrictions.eq("id", id));
		criteria.add(Restrictions.eq("status", StatusProdutoEnum.ATIVO));
		
		Disjunction or = Restrictions.disjunction();
		or.add(Restrictions.eq("usuario", UsuarioSessaoUtil.getUsuario()));
		or.add(Restrictions.eq("usuario", UsuarioSessaoUtil.getUsuario().getUsuarioPai()));
		
		criteria.add(or);
		return (Produto) persistence.pesquisarCriteriaUnique(criteria);
	}*/
}
