package net.inforgyn.neg;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import net.inforgyn.adapter.BaixaRecebimentoAdapter;
import net.inforgyn.adapter.MovimentoAdapter;
import net.inforgyn.constante.SituacaoPagamentoEnum;
import net.inforgyn.impl.EntityPersistence;
import net.inforgyn.model.BaixaRecebimento;
import net.inforgyn.model.Credito;
import net.inforgyn.model.Usuario;
import net.inforgyn.persistence.Transactional;
import net.inforgyn.qualifierCdi.ListCancelarRecementoQ;
import net.inforgyn.repository.BaixaRecebimentoDao;

public class BaixaRecebimentoNeg {
	@Inject
	private ContaReceberNeg contaNeg;
	@Inject
	private CreditoNeg creditoNeg;
	@Inject
	private BaixaRecebimentoDao dao;

	public BaixaRecebimentoNeg() {
		super();
	}

	@Transactional
	public void cancelarBaixaRecebimento(List<BaixaRecebimento> cancelarBaixas) {
		dao.excluir(cancelarBaixas);
		contaNeg.atualizarStatusContas(cancelarBaixas);
		creditoNeg.atualizarStatusDebitos(cancelarBaixas);
	}

	public List<BaixaRecebimento> gerarBaixaRecebimento(
			Map<String, Object> filtro) {
		List<Credito> creditos = creditoNeg.listarCreditosAbertos(filtro);
		List<BaixaRecebimento> baixas = new LinkedList<BaixaRecebimento>();

		for (Credito credito : creditos) {
			if (credito.getSituacao().equals(SituacaoPagamentoEnum.PAG_PARCIAL)) {
				// credito = creditoNeg.carregarRecebimentos(credito);
				credito.setRecebimentos(creditoNeg.listarRecebimentos(credito));
			}
			baixas.add(BaixaRecebimentoAdapter.gerarBaixa(credito));
		}
		return baixas;
	}

	public List<Integer> listarAnos() {
		return dao.listarAnos();
	}

	/** Será listado apenas recebimentos realizados no dia **/
	@Produces
	@ListCancelarRecementoQ
	public List<BaixaRecebimento> listarRecebimentosDia() {
		return dao.listarRecebimentosDia();
	}

	public List<BaixaRecebimento> pesquisarRecebimentos(
			Map<String, Object> filtro) {
		return dao.pesquisarRecebimentos(filtro);
	}

	@Transactional
	public void salvar(List<BaixaRecebimento> baixas) {
		List<EntityPersistence> entitys = new ArrayList<EntityPersistence>();

		for (BaixaRecebimento baixa : baixas) {
			Credito credito = (Credito) dao.pesquisarPorId(Credito.class, baixa.getCredito().getId());
			BigDecimal valorPago = credito.getValorPago();

			if (baixa.getValor().equals(credito.getValor().subtract(valorPago))) {
				credito.setSituacao(SituacaoPagamentoEnum.QUITADO);
			} else if (baixa.getValor().longValue() < credito.getValor()
					.subtract(valorPago).longValue()) {
				credito.setSituacao(SituacaoPagamentoEnum.PAG_PARCIAL);
			}
			baixa.setCredito(credito);

			entitys.add(baixa);
			entitys.add(MovimentoAdapter.gerar(baixa));
		}

		dao.salvar(entitys);
		contaNeg.atualizarStatusContas(baixas);
	}

	public Map<String, BigDecimal> valorTotalPesquisa(Map<String, Object> filtro) {
		return dao.valorTotalPesquisa(filtro);
	}
}
