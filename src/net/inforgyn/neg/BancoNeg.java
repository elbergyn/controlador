package net.inforgyn.neg;

import java.util.List;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import net.inforgyn.model.Banco;
import net.inforgyn.repository.BancoDao;
import net.inforgyn.util.StringUtil;
import net.inforgyn.util.UsuarioSessaoUtil;

public class BancoNeg {
	@Inject
	private BancoDao dao;

	public Banco alterar(Banco banco) {
		dao.alterar(banco);
		return banco;
	}

	public Banco salvar(Banco banco) {
		banco.setDescricao(StringUtil.captalizar(banco.getDescricao()));
		banco.setUsuario(UsuarioSessaoUtil.getUsuario());
		dao.salvar(banco);
		return banco;
	}

	public void excluir(Banco banco) {
		dao.excluir(banco);
	}

	@Produces
	public List<Banco> listarBancos() {
		return dao.listarPorUsuarioComPai(Banco.class, UsuarioSessaoUtil.getUsuario());
	}
}
