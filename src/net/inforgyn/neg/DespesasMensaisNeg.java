package net.inforgyn.neg;

import java.util.List;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import net.inforgyn.model.ContaPagar;
import net.inforgyn.model.DespesaMensal;
import net.inforgyn.repository.DespesasMensaisDao;
import net.inforgyn.util.UsuarioSessaoUtil;

public class DespesasMensaisNeg {
	@Inject
	private DespesasMensaisDao dao;

	public DespesaMensal alterar(DespesaMensal despesa) {
		dao.alterar(despesa);
		return (DespesaMensal) despesa;
	}

	public DespesaMensal salvar(DespesaMensal despesa) {
		despesa.setUsuario(UsuarioSessaoUtil.getUsuario());
		dao.salvar(despesa);

		return despesa;
	}

	public void excluir(DespesaMensal despesa) {
		dao.excluir(despesa);
	}

	@Produces
	public List<DespesaMensal> listarDespesas() {
		return dao.listarPorUsuarioComPai(DespesaMensal.class, UsuarioSessaoUtil.getUsuario());
	}
}
