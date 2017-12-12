package net.inforgyn.neg;

import java.util.List;

import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import net.inforgyn.model.BandeiraCartao;
import net.inforgyn.repository.BandeiraCartaoDao;
import net.inforgyn.util.UsuarioSessaoUtil;
@Default
public class BandeiraCartaoNeg {
	@Inject
	private BandeiraCartaoDao dao;

	public BandeiraCartao alterar(BandeiraCartao bandeira) {
		dao.alterar(bandeira);
		return bandeira;
	}

	public BandeiraCartao salvar(BandeiraCartao bandeira) {
		bandeira.setUsuario(UsuarioSessaoUtil.getUsuario());
		dao.salvar(bandeira);
		return bandeira;
	}

	public void excluir(BandeiraCartao bandeira) {
		dao.excluir(bandeira);
	}

	@Produces
	public List<BandeiraCartao> listarBandeiras() {
		return dao.listarPorUsuarioComPai(BandeiraCartao.class, UsuarioSessaoUtil.getUsuario());
	}
}
