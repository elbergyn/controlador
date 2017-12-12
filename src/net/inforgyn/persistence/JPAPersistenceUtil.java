package net.inforgyn.persistence;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import net.inforgyn.constante.MessageUtilEnum;
import net.inforgyn.exception.DaoException;
import net.inforgyn.exception.PermissaoExclusaoException;
import net.inforgyn.exception.ValorDuplicadoException;
import net.inforgyn.impl.EntityPersistence;
import net.inforgyn.model.Usuario;
import net.inforgyn.util.UsuarioSessaoUtil;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

public class JPAPersistenceUtil {
	@Inject
	private EntityManager em;

	@Transactional
	public EntityPersistence atualizar(EntityPersistence obj) {
		if (obj.getId() != null) {
			try {
				em.merge(obj);
				flush();
			} catch (Exception e) {
				e.printStackTrace();
				throw new DaoException(e,
						MessageUtilEnum.ERRO_PERSISTENCIA_ALTERAR.getMensagem());
			}
			return obj;
		} else {
			return salvar(obj);
		}
	}

	@Transactional
	public void atualizar(List<EntityPersistence> lista) {
		try {
			for (EntityPersistence entity : lista) {
				em.merge(entity);
			}
			flush();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e,
					MessageUtilEnum.ERRO_PERSISTENCIA_ALTERAR.getMensagem());
		}
	}

	@Transactional
	public boolean atualizarHql(String hql, Map<String, Object> parametros) {
		try {
			Query query = em.createQuery(hql);
			if (parametros != null && !parametros.isEmpty()) {
				Entry<String, Object> mapEntry = null;

				for (Iterator iterator = parametros.entrySet().iterator(); iterator
						.hasNext();) {
					mapEntry = (Entry) iterator.next();
					query.setParameter((String) mapEntry.getKey(),
							mapEntry.getValue());
				}
			}
			Integer result = query.executeUpdate();
			flush();
			return result > 0;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e,
					MessageUtilEnum.ERRO_PERSISTENCIA_ALTERAR.getMensagem());
		}
	}

	public void atualizarMergeNamedQuery(String namedQuery,
			Map<String, Object> namedParams) {
		try {
			Query query = em.createNamedQuery(namedQuery);
			if (namedParams != null) {
				Entry<String, Object> mapEntry = null;
				for (Iterator it = namedParams.entrySet().iterator(); it
						.hasNext(); query.setParameter(
						(String) mapEntry.getKey(), mapEntry.getValue())) {
				}
			}
			query.executeUpdate();
			em.flush();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e,
					MessageUtilEnum.ERRO_PERSISTENCIA_ALTERAR.getMensagem());
		}
	}

	public EntityPersistence carregarLazy(EntityManager em,
			EntityPersistence entity, String... relacoes) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("from ").append(entity.getClass().getName())
					.append(" entity ");

			for (String s : relacoes) {
				sb.append(" left join fetch entity.").append(s);
			}
			sb.append(" where entity.id = ").append(entity.getId());

			Query query = em.createQuery(sb.toString());
			return (EntityPersistence) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public EntityPersistence carregarLazy(EntityPersistence entity,
			String... relacoes) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("from ").append(entity.getClass().getName())
					.append(" entity ");

			for (String s : relacoes) {
				sb.append(" left join fetch entity.").append(s);
			}
			sb.append(" where entity.id = ").append(entity.getId());
			Query query = em.createQuery(sb.toString());
			return (EntityPersistence) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public <T> List<T> carregarLazy(List entitys, String... relacoes) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("from ").append(entitys.get(0).getClass().getName())
					.append(" entity ");

			for (String s : relacoes) {
				sb.append(" inner join fetch entity.").append(s);
			}
			sb.append(" where entity.id in (");

			for (Iterator iterator = entitys.iterator(); iterator.hasNext();) {
				EntityPersistence e = (EntityPersistence) iterator.next();
				sb.append(e.getId());
				if (iterator.hasNext()) {
					sb.append(",");
				}
			}
			sb.append(")");

			Query query = em.createQuery(sb.toString(), entitys.get(0)
					.getClass());
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Long contar(String condicao, Class obj,
			Map<String, Object> parametros) {
		StringBuilder sb = new StringBuilder();
		sb.append("select count(id) from ");
		sb.append(obj.getName());
		sb.append(condicao);

		try {
			Query query = em.createQuery(sb.toString());

			if (parametros != null && !parametros.isEmpty()) {
				Entry<String, Object> mapEntry = null;

				for (Iterator iterator = parametros.entrySet().iterator(); iterator
						.hasNext();) {
					mapEntry = (Entry) iterator.next();
					query.setParameter((String) mapEntry.getKey(),
							mapEntry.getValue());
				}
			}

			Object total = query.getSingleResult();

			return (Long) total;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e,
					MessageUtilEnum.ERRO_PERSISTENCIA_PESQUISAR.getMensagem());
		}
	}

	public void evict(EntityPersistence entity) {
		Session session = (Session) em.getDelegate();
		session.evict(entity);
	}

	@Transactional
	public void excluir(EntityPersistence obj) {
		try {
			obj = em.find(obj.getClass(), obj.getId());
			em.remove(obj);
			em.flush();
		} catch (Exception e) {
			e.printStackTrace();
			String except = e.getCause().toString() + " "
					+ e.getCause().getCause().toString();
			if (except.contains("ConstraintViolationException")) {
				throw new PermissaoExclusaoException(
						MessageUtilEnum.ERRO_PERSISTENCIA_EXCLUIR_CONSTRAINT
								.getMensagem());
			} else {
				throw new DaoException(e,
						MessageUtilEnum.ERRO_PERSISTENCIA_EXCLUIR.getMensagem());
			}
		}
	}

	@Transactional
	public void excluir(EntityPersistence obj, String... carregar) {

		obj = carregarLazy(em, obj, carregar);

		try {
			em.remove(obj);
			em.flush();
		} catch (Exception e) {
			e.printStackTrace();
			String except = e.getCause().toString() + " "
					+ e.getCause().getCause().toString();
			if (except.contains("ConstraintViolationException")) {
				throw new PermissaoExclusaoException(
						MessageUtilEnum.ERRO_PERSISTENCIA_EXCLUIR_CONSTRAINT
								.getMensagem());
			}

			throw new DaoException(e,
					MessageUtilEnum.ERRO_PERSISTENCIA_EXCLUIR.getMensagem());
		}
	}

	@Transactional
	public void excluir(List lista) {
		try {
			for (Iterator iterator = lista.iterator(); iterator.hasNext();) {
				EntityPersistence entity = (EntityPersistence) iterator.next();
				entity = em.getReference(entity.getClass(), entity.getId());
				em.remove(entity);
			}
			em.flush();
		} catch (Exception e) {
			e.printStackTrace();
			String except = e.getCause().toString() + " "
					+ e.getCause().getCause().toString();
			if (except.contains("ConstraintViolationException")) {
				throw new PermissaoExclusaoException(
						MessageUtilEnum.ERRO_PERSISTENCIA_EXCLUIR_CONSTRAINT
								.getMensagem());
			}

			throw new DaoException(e,
					MessageUtilEnum.ERRO_PERSISTENCIA_EXCLUIR.getMensagem());
		}
	}

	public List<Object> executarPesquisaHql(String hql,
			Map<String, Object> filtro) {

		try {
			Query query = em.createQuery(hql);
			if (filtro != null && !filtro.isEmpty()) {
				Entry<String, Object> mapEntry = null;

				for (Iterator iterator = filtro.entrySet().iterator(); iterator
						.hasNext();) {
					mapEntry = (Entry) iterator.next();
					query.setParameter((String) mapEntry.getKey(),
							mapEntry.getValue());
				}
			}
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e,
					MessageUtilEnum.ERRO_PERSISTENCIA_PESQUISAR.getMensagem());
		}
	}

	public <T> List<T> executarPesquisaHql(String hql,
			Map<String, Object> parametros, Class clazz) {
		try {
			TypedQuery query = em.createQuery(hql, clazz);
			if (parametros != null && !parametros.isEmpty()) {
				Entry<String, Object> mapEntry = null;

				for (Iterator iterator = parametros.entrySet().iterator(); iterator
						.hasNext();) {
					mapEntry = (Entry) iterator.next();
					query.setParameter((String) mapEntry.getKey(),
							mapEntry.getValue());
				}
			}
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e,
					MessageUtilEnum.ERRO_PERSISTENCIA_PESQUISAR.getMensagem());
		}
	}

	public void flush() {
		em.flush();
	}

	public Criteria getCriteria(Class clazz) {
		Session session = em.unwrap(Session.class);
		return session.createCriteria(clazz);
	}

	public EntityManager getEntityManager() {
		return em;
	}

	public <T> List<T> listarComQtde(Class<EntityPersistence> class1,
			Usuario usuario, int total, String... carregar) {
		StringBuilder sb = new StringBuilder("from ");
		sb.append(class1.getName());
		sb.append(" where usuario = :usuario ");

		try {
			TypedQuery query = em.createQuery(sb.toString(), class1);
			query.setParameter("usuario", usuario);
			query.setMaxResults(total);
			return carregarLazy(query.getResultList(), carregar);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e,
					MessageUtilEnum.ERRO_PERSISTENCIA_PESQUISAR.getMensagem());
		}
	}

	public <T> List<T> listarComQtde(Class<T> class1, Usuario usuario, int total) {
		StringBuilder sb = new StringBuilder("from ");
		sb.append(class1.getName());
		sb.append(" where usuario = :usuario ");

		try {
			TypedQuery query = em.createQuery(sb.toString(), class1);
			query.setParameter("usuario", usuario);
			query.setMaxResults(total);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e,
					MessageUtilEnum.ERRO_PERSISTENCIA_PESQUISAR.getMensagem());
		}
	}

	public <T> List<T> listarComQtdeOrder(Class<T> class1, Usuario usuario,
			int total, String ordenar) {
		StringBuilder sb = new StringBuilder("from ");
		sb.append(class1.getName());
		sb.append(" where usuario = :usuario ");
		sb.append(" order by ");
		sb.append(ordenar);

		try {
			TypedQuery query = em.createQuery(sb.toString(), class1);
			query.setParameter("usuario", usuario);
			query.setMaxResults(total);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e,
					MessageUtilEnum.ERRO_PERSISTENCIA_PESQUISAR.getMensagem());
		}
	}

	public <T> List<T> listarNamedQuery(String query) {
		try {
			return em.createNamedQuery(query).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e,
					MessageUtilEnum.ERRO_PERSISTENCIA_PESQUISAR.getMensagem());
		}
	}

	public <T> List<T> listarPorUsuarioComPai(Class class1, Usuario usuario) {
		try {
			/*
			 * TypedQuery query = em.createQuery("from " + class1.getName() +
			 * " where usuario = :usuario", class1);
			 * query.setParameter("usuario", usuario); return
			 * query.getResultList();
			 */
			Criteria criteria = getCriteria(class1);

			Disjunction or = Restrictions.disjunction();
			or.add(Restrictions.eq("usuario", usuario));
			or.add(Restrictions.eq("usuario", usuario.getUsuarioPai()));
			criteria.add(or);

			return criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e,
					MessageUtilEnum.ERRO_PERSISTENCIA_PESQUISAR.getMensagem());
		}
	}

	public <T> List<T> listarPorUsuario(Class class1, Usuario usuario) {
		try {
			/*
			 * TypedQuery query = em.createQuery("from " + class1.getName() +
			 * " where usuario = :usuario", class1);
			 * query.setParameter("usuario", usuario); return
			 * query.getResultList();
			 */
			Criteria criteria = getCriteria(class1);

			criteria.add(Restrictions.eq("usuario", usuario));

			return criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e,
					MessageUtilEnum.ERRO_PERSISTENCIA_PESQUISAR.getMensagem());
		}
	}

	public boolean listInicializado(List lista) {
		return em.getEntityManagerFactory().getPersistenceUnitUtil()
				.isLoaded(lista);
	}

	public <T> List<T> pesquisaHql(String hql, Map<String, Object> parametros) {
		try {
			Query query = em.createQuery(hql);
			if (parametros != null && !parametros.isEmpty()) {
				Entry<String, Object> mapEntry = null;

				for (Iterator iterator = parametros.entrySet().iterator(); iterator
						.hasNext();) {
					mapEntry = (Entry) iterator.next();
					query.setParameter((String) mapEntry.getKey(),
							mapEntry.getValue());
				}
			}
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e,
					MessageUtilEnum.ERRO_PERSISTENCIA_PESQUISAR.getMensagem());
		}
	}

	public <T> List<T> pesquisaHql(String hql, Map<String, Object> parametros,
			Class clazz) {
		try {
			TypedQuery query = em.createQuery(hql, clazz);
			if (parametros != null && !parametros.isEmpty()) {
				Entry<String, Object> mapEntry = null;

				for (Iterator iterator = parametros.entrySet().iterator(); iterator
						.hasNext();) {
					mapEntry = (Entry) iterator.next();
					query.setParameter((String) mapEntry.getKey(),
							mapEntry.getValue());
				}
			}
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e,
					MessageUtilEnum.ERRO_PERSISTENCIA_PESQUISAR.getMensagem());
		}
	}

	public EntityPersistence pesquisar(EntityPersistence obj) {
		try {
			return em.find(obj.getClass(), obj.getId());
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e,
					MessageUtilEnum.ERRO_PERSISTENCIA_PESQUISAR.getMensagem());
		}
	}

	public Object pesquisarCriteriaUnique(Criteria criteria) {
		try {
			return criteria.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e,
					MessageUtilEnum.ERRO_PERSISTENCIA_PESQUISAR.getMensagem());
		}
	}

	public Object pesquisarCriteriaList(Criteria criteria) {
		try {
			return criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e,
					MessageUtilEnum.ERRO_PERSISTENCIA_PESQUISAR.getMensagem());
		}
	}

	public List<EntityPersistence> pesquisarHql(String hql,
			Map<String, Object> parametros) {

		try {
			Query query = em.createQuery(hql);
			if (parametros != null && !parametros.isEmpty()) {
				Entry<String, Object> mapEntry = null;

				for (Iterator iterator = parametros.entrySet().iterator(); iterator
						.hasNext();) {
					mapEntry = (Entry) iterator.next();
					query.setParameter((String) mapEntry.getKey(),
							mapEntry.getValue());
				}
			}
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e,
					MessageUtilEnum.ERRO_PERSISTENCIA_PESQUISAR.getMensagem());
		}
	}

	public <T> List<T> pesquisarHql(String hql, Map<String, Object> parametros,
			Class clazz) {
		try {
			Query query = em.createQuery(hql, clazz);
			if (parametros != null && !parametros.isEmpty()) {
				Entry<String, Object> mapEntry = null;

				for (Iterator iterator = parametros.entrySet().iterator(); iterator
						.hasNext();) {
					mapEntry = (Entry) iterator.next();
					query.setParameter((String) mapEntry.getKey(),
							mapEntry.getValue());
				}
			}
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e,
					MessageUtilEnum.ERRO_PERSISTENCIA_PESQUISAR.getMensagem());
		}
	}

	public Object pesquisarHqlUnique(String hql, Map<String, Object> parametros) {

		try {
			Query query = em.createQuery(hql);
			if (parametros != null && !parametros.isEmpty()) {
				Entry<String, Object> mapEntry = null;

				for (Iterator iterator = parametros.entrySet().iterator(); iterator
						.hasNext();) {
					mapEntry = (Entry) iterator.next();
					query.setParameter((String) mapEntry.getKey(),
							mapEntry.getValue());
				}
			}
			List list = query.getResultList();
			if (!list.isEmpty()) {
				return list.get(0);
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e,
					MessageUtilEnum.ERRO_PERSISTENCIA_PESQUISAR.getMensagem());
		}
	}

	public EntityPersistence pesquisarHqlUnique(String hql,
			Map<String, Object> parametros, Class<Usuario> class1) {
		try {
			TypedQuery query = em.createQuery(hql, class1);
			if (parametros != null && !parametros.isEmpty()) {
				Entry<String, Object> mapEntry = null;

				for (Iterator iterator = parametros.entrySet().iterator(); iterator
						.hasNext();) {
					mapEntry = (Entry) iterator.next();
					query.setParameter((String) mapEntry.getKey(),
							mapEntry.getValue());
				}
			}
			List list = query.getResultList();
			if (!list.isEmpty()) {
				return (EntityPersistence) list.get(0);
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e,
					MessageUtilEnum.ERRO_PERSISTENCIA_PESQUISAR.getMensagem());
		}
	}

	public List pesquisarListCriteria(Criteria criteria) {
		Disjunction or = Restrictions.disjunction();
		or.add(Restrictions.eq("usuario", UsuarioSessaoUtil.getUsuario()));
		if (UsuarioSessaoUtil.getUsuario().getUsuarioPai() != null
				&& UsuarioSessaoUtil.getUsuario().getUsuarioPai().getId() > 1) {
			or.add(Restrictions.eq("usuario", UsuarioSessaoUtil.getUsuario().getUsuarioPai()));
		}
		criteria.add(or);
		criteria.add(Restrictions.eq("usuario", UsuarioSessaoUtil.getUsuario()));
		
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public EntityPersistence pesquisarPorId(Class classe, Long id) {
		try {
			return em.find(classe, id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e,
					MessageUtilEnum.ERRO_PERSISTENCIA_PESQUISAR.getMensagem());
		}
	}

	public EntityPersistence pesquisarPorId(EntityPersistence obj) {
		try {
			return em.find(obj.getClass(), obj.getId());
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e,
					MessageUtilEnum.ERRO_PERSISTENCIA_PESQUISAR.getMensagem());
		}
	}

	public EntityPersistence pesquisarPorId(EntityPersistence obj, Long id) {
		try {
			return em.find(obj.getClass(), id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e,
					MessageUtilEnum.ERRO_PERSISTENCIA_PESQUISAR.getMensagem());
		}
	}

	public <T> List<T> pesquisarSql(String sql) {
		try {
			return em.createQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e,
					MessageUtilEnum.ERRO_PERSISTENCIA_EXCLUIR.getMensagem());
		}
	}

	@Transactional
	public EntityPersistence salvar(EntityPersistence entity){
		try {
			em.persist(entity);
			em.flush();
		}catch (Exception e) {
			entity.setId(null);
			e.printStackTrace();
			String except = e.getCause().toString() + " "
					+ e.getCause().getCause().toString();
			if (except.contains("ConstraintViolationException")) {
				String erro = except.split("Detalhe: ")[1];
				throw new ValorDuplicadoException(e,
						MessageUtilEnum.ERRO_PERSISTENCIA_SALVAR_CONSTRAINT
								.getMensagem()+"\n "+erro);
			} else {
				throw new DaoException(e,
					MessageUtilEnum.ERRO_PERSISTENCIA_SALVAR.getMensagem());
			}
		}
		return entity;
	}

	@Transactional
	public EntityPersistence salvar(EntityPersistence entity,
			EntityTransaction transaction) {
		try {
			em.persist(entity);
			flush();
		} catch (Exception e) {
			entity.setId(null);
			e.printStackTrace();
			String except = e.getCause().toString() + " "
					+ e.getCause().getCause().toString();
			if (except.contains("ConstraintViolationException")) {
				String erro = except.split("Detalhe: ")[1];
				throw new ValorDuplicadoException(e,
						MessageUtilEnum.ERRO_PERSISTENCIA_SALVAR_CONSTRAINT
								.getMensagem()+"\n "+erro);
			} else {
				throw new DaoException(e,
					MessageUtilEnum.ERRO_PERSISTENCIA_SALVAR.getMensagem());
			}
		}
		return entity;
	}

	@Transactional
	public <T> void salvar(List<T> lista) {
		try {
			for (Object e : lista) {
				EntityPersistence ep = (EntityPersistence)e;
				if(ep.getUsuario() == null){
					ep.setUsuario(UsuarioSessaoUtil.getUsuario());
				}
				em.persist(ep);
			}
			flush();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e,
					MessageUtilEnum.ERRO_PERSISTENCIA_SALVAR.getMensagem());
		}
	}

	public boolean validarEntityHql(String hql, Map<String, Object> parametros) {

		try {
			Query query = em.createQuery(hql);
			if (parametros != null && !parametros.isEmpty()) {
				Entry<String, Object> mapEntry = null;

				for (Iterator iterator = parametros.entrySet().iterator(); iterator
						.hasNext();) {
					mapEntry = (Entry) iterator.next();
					query.setParameter((String) mapEntry.getKey(),
							mapEntry.getValue());
				}
			}
			Integer total = query.getResultList().size();
			if (total > 0) {
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e,
					MessageUtilEnum.ERRO_PERSISTENCIA_PESQUISAR.getMensagem());
		}
	}

}
