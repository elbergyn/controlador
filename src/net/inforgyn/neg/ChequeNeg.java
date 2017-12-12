package net.inforgyn.neg;

import java.util.List;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import net.inforgyn.model.Cheque;
import net.inforgyn.repository.ChequeDao;
import net.inforgyn.util.UsuarioSessaoUtil;

public class ChequeNeg {
	@Inject
	private ChequeDao dao;

	public Cheque alterar(Cheque cheque) {
		dao.alterar(cheque);
		return (Cheque) cheque;
	}

	public Cheque salvar(Cheque cheque) {
		cheque.setUsuario(UsuarioSessaoUtil.getUsuario());
		dao.salvar(cheque);
		return (Cheque) cheque;
	}

	public void excluir(Cheque cheque) {
		dao.excluir(cheque);
	}
	
	@Produces
	public List<Cheque> listarCheques() {
		return dao.listarPorUsuarioComPai(Cheque.class, UsuarioSessaoUtil.getUsuario());
	}

	public Cheque pesquisarPorId(Cheque cheque) {
		return (Cheque) dao.pesquisarPorId(cheque.getClass(), cheque.getId());
	}
}
