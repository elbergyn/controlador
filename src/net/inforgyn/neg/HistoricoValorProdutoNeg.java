package net.inforgyn.neg;

import java.util.List;

import javax.inject.Inject;

import net.inforgyn.model.HistoricoValorProduto;
import net.inforgyn.model.ValorProduto;
import net.inforgyn.model.filterPesquisa.FilterHistoricoValor;
import net.inforgyn.repository.HistoricoValorProdutoDao;
import net.inforgyn.util.UsuarioSessaoUtil;

public class HistoricoValorProdutoNeg {
	@Inject
	private HistoricoValorProdutoDao dao;

	/** Gera histórico do valor caso tenha tido alteração nos valores **/
	public void gerarHistorico(ValorProduto valor) {

		ValorProduto gravado = pesquisarPorId(valor.getId());
		if (gravado.getValorCusto().doubleValue() != valor.getValorCusto().doubleValue()
				|| gravado.getValorVenda().doubleValue() != valor.getValorVenda().doubleValue()
				|| gravado.getLucroPercentual().doubleValue() != valor.getLucroPercentual().doubleValue()) {
			HistoricoValorProduto historico = new HistoricoValorProduto();
			historico.setLucro(valor.getLucroPercentual());
			historico.setProduto(valor.getProduto());
			historico.setUsuario(UsuarioSessaoUtil.getUsuario());
			historico.setValorCusto(valor.getValorCusto());
			historico.setValorVenda(valor.getValorVenda());
			dao.salvar(historico);
		}

	}

	public ValorProduto pesquisarPorId(Long id) {
		return (ValorProduto) dao.pesquisarPorId(ValorProduto.class, id);
	}

	public List<HistoricoValorProduto> listarProdutosComHistorico(
			FilterHistoricoValor filtro) {
		return dao.listarProdutosComHistorico(filtro);
	}

}
