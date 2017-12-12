package net.inforgyn.neg;

import java.util.List;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import net.inforgyn.model.Fornecedor;
import net.inforgyn.repository.FornecedorDao;
import net.inforgyn.util.StringUtil;
import net.inforgyn.util.UsuarioSessaoUtil;

public class FornecedorNeg {
	@Inject	private FornecedorDao dao;

	public Fornecedor alterar(Fornecedor fornecedor) {
		dao.alterar(fornecedor);
		return fornecedor;
	}

	public Fornecedor salvar(Fornecedor fornecedor) {
		fornecedor.setDescricao(StringUtil.captalizar(fornecedor.getDescricao()));
		fornecedor.setUsuario(UsuarioSessaoUtil.getUsuario());
		dao.salvar(fornecedor);
		return fornecedor;
	}

	public void excluir(Fornecedor fornecedor) {
		dao.excluir(fornecedor);
	}

	@Produces
	public List<Fornecedor> listarFornecedores() {
		return dao.listarPorUsuarioComPai(Fornecedor.class, UsuarioSessaoUtil.getUsuario());
	}
}
