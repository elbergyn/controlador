package net.inforgyn.solid;

import net.inforgyn.constante.TipoLancamentoEnum;
import net.inforgyn.impl.MovimentoEstoqueInface;
import net.inforgyn.model.Estoque;
import net.inforgyn.model.MovimentoEstoque;
import net.inforgyn.util.UsuarioSessaoUtil;

public class MovimentoEstoqueSaida implements MovimentoEstoqueInface {
	private Estoque estoque;

	public MovimentoEstoqueSaida(Estoque estoque) {
		this.estoque = estoque;
	}

	@Override
	public MovimentoEstoque movimentar() {
		MovimentoEstoque me = new MovimentoEstoque();
		me.setProduto(estoque.getProduto());
		me.setQtde(estoque.getQtde());
		me.setUsuario(UsuarioSessaoUtil.getUsuario());
		me.setTipoLancamento(TipoLancamentoEnum.SAIDA);
		return me;
	}

}
