package net.inforgyn.solid;

import net.inforgyn.constante.TipoRecebimentoEnum;
import net.inforgyn.impl.DebitoInface;
import net.inforgyn.model.ContaReceber;
import net.inforgyn.qualifierCdi.GerarDebitoQ;

@GerarDebitoQ
public class GerarCredito implements DebitoInface {
	protected ContaReceber conta;
	
	public GerarCredito(ContaReceber conta){
		super();
		this.conta = conta;
	}

	@Override
	public void gerar(){
		DebitoInface debito = null;
		if(conta.getTipoRecebimento().equals(TipoRecebimentoEnum.DINHEIRO)){
			debito = new GerarCreditoDinheiro(conta);
		}else if(conta.getTipoRecebimento().equals(TipoRecebimentoEnum.CHEQUE)){
			debito = new GerarCreditoCheque(conta);
		}
		debito.gerar();
	}
}
