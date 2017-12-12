package net.inforgyn.neg;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import net.inforgyn.constante.TipoLancamentoCaixaEnum;
import net.inforgyn.model.Caixa;
import net.inforgyn.model.CaixaFechamento;
import net.inforgyn.model.CaixaMovimento;
import net.inforgyn.repository.CaixaMovimentoDao;

public class CaixaMovimentoNeg {
	@Inject
	private CaixaMovimentoDao dao;

	public List<CaixaMovimento> carregarMovimentos(Caixa caixa) {
		return dao.carregarMovimentos(caixa);
	}

	public CaixaMovimento salvar(CaixaMovimento movimento) {
		return (CaixaMovimento) dao.salvar(movimento);
	}

	public BigDecimal calcularSaldo(List<CaixaMovimento> movimentos) {
		BigDecimal entrada = new BigDecimal(0);
		BigDecimal saida = new BigDecimal(0);
		for (CaixaMovimento cm : movimentos) {
			if (cm.getTipo().equals(TipoLancamentoCaixaEnum.ABERTURA)
					|| cm.getTipo().equals(TipoLancamentoCaixaEnum.ENTRADA)) {
				entrada = entrada.add(cm.getValor());
			} else if (cm.getTipo().equals(TipoLancamentoCaixaEnum.SAIDA)
					|| cm.getTipo().equals(TipoLancamentoCaixaEnum.AJUSTE)) {
				saida = saida.add(cm.getValor());
			}
		}
		return entrada.subtract(saida);
	}

}
