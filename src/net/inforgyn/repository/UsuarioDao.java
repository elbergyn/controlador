package net.inforgyn.repository;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import net.inforgyn.impl.EntityPersistence;
import net.inforgyn.model.CategoriaDespesa;
import net.inforgyn.model.ParametrosSistema;
import net.inforgyn.model.TipoRecebimento;
import net.inforgyn.model.Usuario;
import net.inforgyn.persistence.JPAPersistenceUtil;
import net.inforgyn.util.Criptografar;

public class UsuarioDao extends DaoModel{
	
	@Inject private CategoriaDespesaDao categoriaDao;

	public EntityPersistence alterarSemSenha(Usuario usuario) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE ");
		sb.append(usuario.getClass().getName());
		sb.append(" usuario set usuario.descricao = :descricao, ");
		sb.append(" usuario.email = :email, ");
		sb.append(" usuario.tipo = :tipo ");
		sb.append(" where id = :id ");
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("descricao", usuario.getDescricao());
		parametros.put("id", usuario.getId());
		parametros.put("email", usuario.getEmail());
		parametros.put("tipo", usuario.getTipo());
		
		persistence.atualizarHql(sb.toString(), parametros);
		return usuario ;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void excluir(EntityPersistence entity) {
		List objetos = new LinkedList<EntityPersistence>();
		objetos.addAll(categoriaDao.listarPorUsuario(CategoriaDespesa.class, (Usuario)entity));
		objetos.addAll(categoriaDao.listarPorUsuario(ParametrosSistema.class, (Usuario)entity));
		objetos.addAll(categoriaDao.listarPorUsuario(TipoRecebimento.class, (Usuario)entity));
		objetos.add(entity);
				
		persistence.excluir(objetos);
	}

	public List<Usuario> listar() {
		return persistence.listarNamedQuery("listarUsuarios");
	}

	@SuppressWarnings("rawtypes")
	@Override
	public EntityPersistence pesquisarPorId(Class classe, Long id) {
		return persistence.pesquisarPorId(classe, id);
	}

	public Usuario logar(Usuario usuario) {
		StringBuilder sb = new StringBuilder();
		sb.append("from ").append(Usuario.class.getName());
		sb.append(" where descricao = :descricao");
		/*sb.append(" where descricaoa = :descricao");*/
		sb.append(" and senha = :senha");
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("descricao", usuario.getDescricao());
		if(usuario.getSenha().length() > 100){//senha já codificada
			parametros.put("senha", usuario.getSenha());
		}else{
			parametros.put("senha", Criptografar.codificar(usuario.getSenha()));
		}
		
		return (Usuario) persistence.pesquisarHqlUnique(sb.toString(), parametros, Usuario.class);
	}
	
	public Usuario localizarUsuario(String email) {
		StringBuilder sb = new StringBuilder("FROM ");
		sb.append(Usuario.class.getName());
		sb.append(" where email = :email");
		
		Map<String, Object> parametro = new HashMap<String, Object>();
		parametro.put("email", email);
		
		return (Usuario) persistence.pesquisarHqlUnique(sb.toString(), parametro, Usuario.class);
	}

	public Usuario pesquisarPorUsuarioSenha(String descricao, String senhaAtual) {
		StringBuilder sb = new StringBuilder();
		sb.append("from ").append(Usuario.class.getName());
		sb.append(" where descricao = :descricao");
		sb.append(" and senha = :senha");
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("descricao", descricao);
		parametros.put("senha", senhaAtual);
		
		return (Usuario) persistence.pesquisarHqlUnique(sb.toString(), parametros, Usuario.class);
	}

	public Boolean confirmarUsuario(String descricao, String senha) {
		StringBuilder sb = new StringBuilder();
		sb.append("update ").append(Usuario.class.getName());
		sb.append(" set confirmado = :confirmado ");
		sb.append(" where descricao = :descricao");
		sb.append(" and senha = :senha");
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("descricao", descricao);
		parametros.put("senha", senha);
		parametros.put("confirmado", true);
		
		Boolean confirmou = persistence.atualizarHql(sb.toString(), parametros);
		return confirmou;
	}
}
