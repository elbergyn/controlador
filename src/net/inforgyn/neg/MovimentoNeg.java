package net.inforgyn.neg;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import net.inforgyn.model.Movimento;
import net.inforgyn.repository.MovimentoDao;
import net.inforgyn.util.UsuarioSessaoUtil;

public class MovimentoNeg {
	@Inject
	private MovimentoDao dao;

	public Movimento alterar(Movimento movimento) {
			dao.alterar(movimento);
		return (Movimento) movimento;
	}

	public Movimento salvar(Movimento movimento) {
		movimento.setUsuario(UsuarioSessaoUtil.getUsuario());
		dao.salvar(movimento);
		return movimento;
	}

	public void excluir(Movimento movimento) {
			dao.excluir(movimento);
	}
	
	@Produces
	public List<Movimento> listarUltimosMovimentos(){
		return dao.listarUltimosMovimentos();
	}

	public Map<String, Object> saldo(Date inicio, Date limite) {
		return dao.saldo(inicio, limite);
	}

	public List<Movimento> listarMovimentos(Map<String, Object> filtro) {
		return dao.listarMovimentos(filtro);
	}
}
