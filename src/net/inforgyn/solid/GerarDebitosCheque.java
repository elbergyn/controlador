package net.inforgyn.solid;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import net.inforgyn.constante.SituacaoPagamentoEnum;
import net.inforgyn.model.ContaPagar;
import net.inforgyn.model.Debito;
import net.inforgyn.model.DebitoCheque;
import net.inforgyn.model.EmitirCheque;
import net.inforgyn.util.DataUtil;

public class GerarDebitosCheque extends GerarDebito {
	
	public GerarDebitosCheque(ContaPagar conta, EmitirCheque emitirCheque) {
		super(conta, emitirCheque);
	}

	@Override
	public void gerar() {
		List<Debito> debitos = new LinkedList<Debito>();

		List<Long> numeros = new LinkedList<Long>(emitirCheque.getNumeros());

		for (int i = 0; i < conta.getParcelaTotal(); i++) {
			DebitoCheque debito = new DebitoCheque();
			debito.setConta(conta);
			debito.setDescricao(conta.getDescricao());
			debito.setLancamento(conta.getDataLancamento());
			debito.setValor(conta.getValorParcelas().get(i).getValor().getBigDecimal());
			debito.setVencimento(conta.getValorParcelas().get(i).getVencimento());
			debito.setUsuario(conta.getUsuario());
			debito.setTipoPagamento(conta.getTipoPagamento());
			debito.setEmitirCheque(new EmitirCheque(emitirCheque.getCheque()));
			debito.getEmitirCheque().setNumeroCheque(numeros.get(i));
			debito.setParcela(conta.getValorParcelas().get(i).getNumero());
			debito.setParcelaTotal(conta.getParcelaTotal());
			debito.setCategoria(conta.getCategoria());
			if(DataUtil.dataAnterior(debito.getVencimento(), new Date())){
				debito.setSituacao(SituacaoPagamentoEnum.ATRASO);
			}else{
				debito.setSituacao(SituacaoPagamentoEnum.A_VENCER);
			}
			debitos.add(debito);
		}
		conta.setDebitos(debitos);
	}
}
