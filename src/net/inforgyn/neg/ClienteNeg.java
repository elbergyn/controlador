package net.inforgyn.neg;

import java.util.List;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import net.inforgyn.model.Cliente;
import net.inforgyn.repository.ClienteDao;
import net.inforgyn.util.UsuarioSessaoUtil;

public class ClienteNeg {
	@Inject	private ClienteDao dao;

	public Cliente alterar(Cliente cliente) {
		dao.alterar(cliente);
		return cliente;
	}

	public Cliente salvar(Cliente cliente) {
		cliente.setUsuario(UsuarioSessaoUtil.getUsuario());
		dao.salvar(cliente);
		return cliente;
	}

	public void excluir(Cliente cliente) {
		dao.excluir(cliente);
	}

	@Produces
	public List<Cliente> listarClientes() {
		return dao.listarPorUsuarioComPai(Cliente.class, UsuarioSessaoUtil.getUsuario());
	}
}
