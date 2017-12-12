package net.inforgyn.neg;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import net.inforgyn.constante.PeriodoPagamentoEnum;
import net.inforgyn.constante.SituacaoPagamentoEnum;
import net.inforgyn.constante.TipoPagamentoEnum;
import net.inforgyn.exception.NegocioException;
import net.inforgyn.model.BaixaPagamento;
import net.inforgyn.model.CartaoCredito;
import net.inforgyn.model.Conta;
import net.inforgyn.model.ContaPagar;
import net.inforgyn.model.Debito;
import net.inforgyn.model.EmitirCheque;
import net.inforgyn.model.Parcela;
import net.inforgyn.repository.ContaPagarDao;
import net.inforgyn.solid.GerarDebito;
import net.inforgyn.solid.Money;
import net.inforgyn.util.DataUtil;
import net.inforgyn.util.UsuarioSessaoUtil;

public class ContaPagarNeg {
	@Inject
	private ContaPagarDao dao;

	public ContaPagar alterar(ContaPagar conta, CartaoCredito cartao,
			EmitirCheque cheque) {
		if (conta.getTipoPagamento().equals(TipoPagamentoEnum.CARTAO)
				&& (cartao == null || cartao.getBandeira() == null)) {
			throw new NegocioException("Deve ser selecionado o cartão");
		} else if (conta.getTipoPagamento().equals(TipoPagamentoEnum.CHEQUE)
				&& (cheque == null || cheque.getCheque() == null)) {
			throw new NegocioException("Deve ser selecionado o cheque");
		}

		if (DataUtil.dataAnterior(conta.getVencimento(),
				DataUtil.getDataAtual())) {
			conta.setSituacao(SituacaoPagamentoEnum.ATRASO);
		} else {
			conta.setSituacao(SituacaoPagamentoEnum.A_VENCER);
		}

		if (conta.getTipoPagamento().equals(TipoPagamentoEnum.CHEQUE)) {
			validarParcelasCheque(conta, cheque);
		}
		new GerarDebito(conta, cartao, cheque).gerar();
		conta = (ContaPagar) dao.alterar(conta);
		return conta;
	}

	public void atualizarStatusContas(List<BaixaPagamento> baixas) {
		dao.atualizarStatusContas(baixas);
	}

	public void calcularParcelamento(ContaPagar conta) {
		conta.setParcelas(new ArrayList<Parcela>());
		if (conta.getValorTotal() != null) {
			Money[] valores = Money.valueOf(conta.getValorTotal()).dividir(
					conta.getParcelaTotal());
			Date[] vencimentos = new Date[conta.getParcelaTotal()];

			vencimentos[0] = conta.getVencimento();
			if (conta.getParcelaTotal() > 1) {
				if (conta.getPeriodoPagamento().equals(
						PeriodoPagamentoEnum.TRINTA)) {
					for (int i = 1; i < conta.getParcelaTotal(); i++) {
						vencimentos[i] = DataUtil.adicionarMes(
								conta.getVencimento(), i * 1);
					}
				} else {
					Date vencimento = conta.getVencimento();
					for (int i = 1; i < conta.getParcelaTotal(); i++) {
						vencimento = DataUtil.adicionarDias(vencimento,
								conta.getPeriodoPagamento());
						vencimentos[i] = vencimento;
					}
				}
			}

			for (int i = 0; i < vencimentos.length; i++) {
				conta.getValorParcelas().add(
						new Parcela(i+1, valores[i], vencimentos[i]));
			}
		}
	}

	public ContaPagar carregarDebitos(ContaPagar conta) {
		return dao.carregarDebitos(conta);
	}

	public ContaPagar carregarParcelamento(ContaPagar conta) {
		conta = carregarDebitos(conta);
		conta.setValorParcelas(new LinkedList<Parcela>());
		int i = 0;
		for(Debito d : conta.getDebitos()){
			conta.getValorParcelas().add(new Parcela(++i, Money.valueOf(d.getValor()), d.getVencimento()));
		}
		return conta;
	}

	public void excluir(ContaPagar conta) {
		dao.excluir(conta, "debitos");
	}

	@Produces
	public List<ContaPagar> listarContaAbertas() {
		return dao.listarEmAberto();
	}

	public List<ContaPagar> listarContas() {
		return dao.listarPorUsuarioComPai(ContaPagar.class, UsuarioSessaoUtil.getUsuario());
	}

	public ContaPagar salvar(ContaPagar conta, CartaoCredito cartao,
			EmitirCheque cheque) {
		if (conta.getTipoPagamento().equals(TipoPagamentoEnum.CARTAO)
				&& (cartao == null || cartao.getBandeira() == null)) {
			throw new NegocioException("Deve ser selecionado o cartão");
		} else if (conta.getTipoPagamento().equals(TipoPagamentoEnum.CHEQUE)
				&& (cheque == null || cheque.getCheque() == null)) {
			throw new NegocioException("Deve ser selecionado o cheque");
		}

		if (DataUtil.dataAnterior(conta.getVencimento(),
				DataUtil.getDataAtual())) {
			conta.setSituacao(SituacaoPagamentoEnum.ATRASO);
		} else {
			conta.setSituacao(SituacaoPagamentoEnum.A_VENCER);
		}

		if (conta.getTipoPagamento().equals(TipoPagamentoEnum.CHEQUE)) {
			validarParcelasCheque(conta, cheque);
		}
		conta.setUsuario(UsuarioSessaoUtil.getUsuario());
		new GerarDebito(conta, cartao, cheque).gerar();
		dao.salvar(conta);
		return conta;
	}

	public void salvar(List<ContaPagar> contas) {
		for (ContaPagar conta : contas) {
			if (DataUtil.dataAnterior(conta.getVencimento(),
					DataUtil.getDataAtual())) {
				conta.setSituacao(SituacaoPagamentoEnum.ATRASO);
			} else {
				conta.setSituacao(SituacaoPagamentoEnum.A_VENCER);
			}

			conta.setUsuario(UsuarioSessaoUtil.getUsuario());
			new GerarDebito(conta).gerar();
		}
		dao.salvar(contas);
	}

	public boolean validarExistencia(ContaPagar conta) {
		return dao.validarExistencia(conta);
	}

	private void validarParcelasCheque(Conta conta, EmitirCheque cheque) {
		if (cheque.getNumeros().size() != conta.getParcelaTotal()) {
			throw new NegocioException(
					"Adicione os cheques de acordo com o número de parcelas");
		}
	}
}
