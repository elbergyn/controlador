package net.inforgyn.neg;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import net.inforgyn.adapter.BaixaPagamentoAdapter;
import net.inforgyn.adapter.MovimentoAdapter;
import net.inforgyn.constante.SituacaoPagamentoEnum;
import net.inforgyn.impl.EntityPersistence;
import net.inforgyn.model.BaixaPagamento;
import net.inforgyn.model.Debito;
import net.inforgyn.model.filterPesquisa.FilterPagamento;
import net.inforgyn.model.filterPesquisa.FiltroBaixaPagamento;
import net.inforgyn.persistence.Transactional;
import net.inforgyn.qualifierCdi.ListCancelarPagamentoQ;
import net.inforgyn.repository.BaixaPagamentoDao;

public class BaixaPagamentoNeg implements Serializable{
	private static final long serialVersionUID = 1L;
	@Inject	private BaixaPagamentoDao dao;
	@Inject private DebitoNeg debitoNeg;
	@Inject private ContaPagarNeg contaNeg;

	public BaixaPagamentoNeg() {
		super();
	}

	@Transactional
	public void salvar(List<BaixaPagamento> baixas) {
		//dao.salvar(baixas);
		List<EntityPersistence> entitys = new ArrayList<EntityPersistence>();
		for (BaixaPagamento baixa : baixas) {
			Debito debito = (Debito) dao.carregarLazy(baixa.getDebito(), "pagamentos");
			BigDecimal valorPago = debito.getValorPago();

			if (baixa.getValor().equals(debito.getValor().subtract(valorPago))) {
				debito.setSituacao(SituacaoPagamentoEnum.QUITADO);
			} else if (baixa.getValor().longValue() < debito.getValor()
					.subtract(valorPago).longValue()) {
				debito.setSituacao(SituacaoPagamentoEnum.PAG_PARCIAL);
			}
			baixa.setDebito(debito);
			entitys.add(baixa);
			entitys.add(MovimentoAdapter.gerar(baixa));
		}
		dao.salvar(entitys);
		
		contaNeg.atualizarStatusContas(baixas);
	}

	@Transactional
	public List<BaixaPagamento> gerarBaixaPagamento(FiltroBaixaPagamento filtro) {
		List<Debito> debitos = debitoNeg.listarDebitosAbertos(filtro);
		List<BaixaPagamento> baixas = new LinkedList<BaixaPagamento>();
		
		for(Debito debito : debitos){
			if(debito.getSituacao().equals(SituacaoPagamentoEnum.PAG_PARCIAL)){
				debito = debitoNeg.carregarPagamentos(debito);
			}
			baixas.add(BaixaPagamentoAdapter.gerarBaixa(debito));
		}
		contaNeg.atualizarStatusContas(baixas);
		return baixas;
	}
	
	/**Será listado apenas pagamentos realizados no dia**/
	@Produces @ListCancelarPagamentoQ
	public List<BaixaPagamento> listarPagamentosDia(){
		return dao.listarPagamentosDia();
	}
		
	public List<BaixaPagamento> pesquisarPagamentos(FilterPagamento filtro) {
		return dao.pesquisarPagamentos(filtro);
	}

	@Transactional
	public void cancelarBaixaPagamento(List<BaixaPagamento> cancelarBaixas) {
		dao.excluir(cancelarBaixas);
		contaNeg.atualizarStatusContas(cancelarBaixas);
		debitoNeg.atualizarStatusDebitos(cancelarBaixas);
	}

	public Map<String, BigDecimal> valorTotalPesquisa(FilterPagamento filtro) {
		return dao.valorTotalPesquisa(filtro);
	}

	public List<Integer> listarAnos() {
		return dao.listarAnos();
	}
}
