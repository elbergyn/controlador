package net.inforgyn.adapter;

import java.util.Date;

import net.inforgyn.constante.TipoMovimentoEnum;
import net.inforgyn.constante.TipoPagamentoEnum;
import net.inforgyn.model.BaixaPagamento;
import net.inforgyn.model.BaixaRecebimento;
import net.inforgyn.model.DebitoCartao;
import net.inforgyn.model.DebitoCheque;
import net.inforgyn.model.Movimento;

public class MovimentoAdapter {
	public static Movimento gerar(BaixaPagamento baixa){
		StringBuilder descricao = new StringBuilder();
		descricao.append(baixa.getDebito().getDescricao());
		
		if(TipoPagamentoEnum.CARTAO.equals(baixa.getDebito().getTipoPagamento())){
			descricao.append(" - ");
			DebitoCartao debito = (DebitoCartao)baixa.getDebito();
			descricao.append(debito.getCartao().getDescricao());
		}
		if(TipoPagamentoEnum.CHEQUE.equals(baixa.getDebito().getTipoPagamento())){
			DebitoCheque debito = (DebitoCheque)baixa.getDebito();
			descricao.append(" - "+debito.getEmitirCheque().getCheque().getDescricao());
		}
		if(baixa.getDebito().getParcelaTotal() > 1){
			descricao.append(" - "+baixa.getDebito().getParcela()+"/"+baixa.getDebito().getParcelaTotal());
		}
		
		Movimento mov = new Movimento();
		mov.setDataLancamento(new Date());		
		mov.setDescricao(descricao.toString());
		mov.setUsuario(baixa.getUsuario());
		mov.setValor(baixa.getValor());
		mov.setTipoMovimento(TipoMovimentoEnum.DEBITO);
		return mov;
	}
	
	public static Movimento gerar(BaixaRecebimento baixa){
		Movimento mov = new Movimento();
		mov.setDataLancamento(new Date());
		mov.setDescricao(baixa.getCredito().getDescricao()+" - "+baixa.getCredito().getDevedor().getDescricao());
		if(baixa.getCredito().getParcelaTotal() > 1){
			mov.setDescricao(mov.getDescricao()+" - "+baixa.getCredito().getParcela()+"/"+baixa.getCredito().getParcelaTotal());
		}
		mov.setUsuario(baixa.getUsuario());
		mov.setValor(baixa.getValor());
		mov.setTipoMovimento(TipoMovimentoEnum.CREDITO);
		return mov;
	}
}
