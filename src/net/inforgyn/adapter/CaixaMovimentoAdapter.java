package net.inforgyn.adapter;

import net.inforgyn.constante.TipoLancamentoCaixaEnum;
import net.inforgyn.model.Caixa;
import net.inforgyn.model.CaixaMovimento;
import net.inforgyn.model.TipoRecebimento;

public class CaixaMovimentoAdapter {
	
	public static CaixaMovimento gerarMovimentoAbertura(Caixa caixa, TipoRecebimento tipo){		
		CaixaMovimento cm = new CaixaMovimento();
		cm.setCaixa(caixa);
		cm.setDescricao("Abertura de caixa");
		cm.setTipo(TipoLancamentoCaixaEnum.ABERTURA);
		cm.setTipoRecebimento(tipo);
		cm.setUsuario(caixa.getUsuario());
		cm.setValor(caixa.getValorInicial());
		return cm;
	}
}
