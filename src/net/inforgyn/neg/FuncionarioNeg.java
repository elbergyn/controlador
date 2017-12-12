package net.inforgyn.neg;

import java.util.List;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import net.inforgyn.model.Funcionario;
import net.inforgyn.repository.FuncionarioDao;
import net.inforgyn.util.UsuarioSessaoUtil;

public class FuncionarioNeg {
	@Inject	private FuncionarioDao dao;

	public Funcionario alterar(Funcionario funcionario) {
		dao.alterar(funcionario);
		return funcionario;
	}

	public Funcionario salvar(Funcionario funcionario) {
		funcionario.setUsuario(UsuarioSessaoUtil.getUsuario());
		dao.salvar(funcionario);
		return funcionario;
	}

	public void excluir(Funcionario funcionario) {
		dao.excluir(funcionario);
	}

	@Produces
	public List<Funcionario> listarFuncionarios() {
		return dao.listarPorUsuarioComPai(Funcionario.class, UsuarioSessaoUtil.getUsuario());
	}
}
