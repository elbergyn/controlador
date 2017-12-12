package net.inforgyn.neg;

import java.util.List;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import net.inforgyn.model.TipoRecebimento;
import net.inforgyn.model.Usuario;
import net.inforgyn.repository.TipoRecebimentoDao;
import net.inforgyn.util.StringUtil;
import net.inforgyn.util.UsuarioSessaoUtil;

public class TipoRecebimentoNeg {
	@Inject
	private TipoRecebimentoDao dao;

	public TipoRecebimento alterar(TipoRecebimento formaRecebimento) {
		dao.alterar(formaRecebimento);
		return formaRecebimento;
	}

	public TipoRecebimento salvar(TipoRecebimento formaRecebimento) {
		formaRecebimento.setDescricao(StringUtil.captalizar(formaRecebimento.getDescricao()));
		formaRecebimento.setUsuario(UsuarioSessaoUtil.getUsuario());
		dao.salvar(formaRecebimento);
		return formaRecebimento;
	}

	public void excluir(TipoRecebimento formaRecebimento) {
		dao.excluir(formaRecebimento);
	}

	@Produces
	public List<TipoRecebimento> listarFormaRecebimentos() {
		return dao.listarPorUsuarioComPai(TipoRecebimento.class, UsuarioSessaoUtil.getUsuario());
	}

	public void gerarTipoRecebimentoPadrao(Usuario usuario) { 
		dao.salvar(new TipoRecebimento(null, "Dinheiro", usuario));
	}
}
