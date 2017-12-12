package net.inforgyn.adapter;

import java.util.Date;

import net.inforgyn.constante.SituacaoPagamentoEnum;
import net.inforgyn.constante.TipoPagamentoEnum;
import net.inforgyn.model.CategoriaDespesa;
import net.inforgyn.model.ContaPagar;
import net.inforgyn.model.DespesaMensal;
import net.inforgyn.util.DataUtil;

public class ContaPagarAdapter {
	public static ContaPagar adapter(DespesaMensal despesa, Integer mes, Integer ano){
		ContaPagar conta = new ContaPagar();
		conta.setDataLancamento(new Date());
		conta.setDescricao(despesa.getDescricao());
		conta.setParcelaTotal(1);
		conta.setSituacao(SituacaoPagamentoEnum.A_VENCER);
		conta.setTipoPagamento(TipoPagamentoEnum.DINHEIRO);
		CategoriaDespesa categoria = new CategoriaDespesa(4L);
		conta.setCategoria(categoria);
		conta.setValorTotal(despesa.getValor());
		conta.setVencimento(DataUtil.criarData(despesa.getDiaVencimento(), mes, ano));
		return conta;
	}
}
