package net.inforgyn.solid;

import net.inforgyn.constante.TipoPagamentoEnum;
import net.inforgyn.impl.DebitoInface;
import net.inforgyn.model.CartaoCredito;
import net.inforgyn.model.ContaPagar;
import net.inforgyn.model.EmitirCheque;
import net.inforgyn.qualifierCdi.GerarDebitoQ;

@GerarDebitoQ
public class GerarDebito implements DebitoInface {
	protected ContaPagar conta;
	protected EmitirCheque emitirCheque;
	protected CartaoCredito cartao;
	
	public GerarDebito(ContaPagar conta, CartaoCredito cartao, EmitirCheque cheque){
		super();
		this.conta = conta;
		this.cartao = cartao;
		this.emitirCheque = cheque;
	}
	
	public GerarDebito(ContaPagar conta) {
		super();
		this.conta = conta;
	}
	
	public GerarDebito(ContaPagar conta, EmitirCheque emitirCheque) {
		super();
		this.conta = conta;
		this.emitirCheque = emitirCheque;
	}
	
	public GerarDebito(ContaPagar conta, CartaoCredito cartao) {
		super();
		this.conta = conta;
		this.cartao = cartao;
	}

	@Override
	public void gerar(){
		DebitoInface debito = null;
		if(conta.getTipoPagamento().equals(TipoPagamentoEnum.DINHEIRO)){
			debito = new GerarDebitosDinheiro(conta);
		}else if(conta.getTipoPagamento().equals(TipoPagamentoEnum.CARTAO)){
			debito = new GerarDebitosCartao(conta, cartao);
		}else if(conta.getTipoPagamento().equals(TipoPagamentoEnum.CHEQUE)){
			debito = new GerarDebitosCheque(conta, emitirCheque);
		}
		debito.gerar();
	}
}
