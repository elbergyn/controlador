package net.inforgyn.neg;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import net.inforgyn.model.ParametrosSistema;
import net.inforgyn.model.Usuario;
import net.inforgyn.repository.ParametrosDao;

@RequestScoped
public class ParametrosNeg {
	@Inject private ParametrosDao dao;
	
	public void salvar(ParametrosSistema parametro){
		dao.salvar(parametro);
	}
	
	public void Alterar(ParametrosSistema parametro){
		dao.alterar(parametro);
	}

	public ParametrosSistema pesquisarPorUsuario(Usuario usuario) {
		return (ParametrosSistema)dao.pesquisarPorId(ParametrosSistema.class, usuario.getParametros().getId());
	}
	
	public ParametrosSistema gerarParametrosPadrao(Usuario usuario){
		ParametrosSistema parametros = new ParametrosSistema();
		parametros.setTema("cruze");
		parametros.setUsuario(usuario);
		//dao.salvar(parametros);
		
		return parametros;
	}
}
