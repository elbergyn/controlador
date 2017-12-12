package net.inforgyn.neg;

import java.util.List;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import net.inforgyn.impl.EntityPersistence;
import net.inforgyn.model.Devedor;
import net.inforgyn.repository.DevedorDao;
import net.inforgyn.util.UsuarioSessaoUtil;

public class DevedorNeg {
	@Inject
	private DevedorDao dao;

	public Devedor alterar(Devedor devedor) {
		dao.alterar(devedor);
		return devedor;
	}

	public Devedor salvar(Devedor devedor) {
		devedor.setUsuario(UsuarioSessaoUtil.getUsuario());
		dao.salvar(devedor);
		return devedor;
	}

	public void excluir(EntityPersistence devedor) {
		dao.excluir(devedor);
	}

	@Produces
	public List<Devedor> listarDevedores() {
		return dao.listarPorUsuarioComPai(Devedor.class, UsuarioSessaoUtil.getUsuario());
	}
}
