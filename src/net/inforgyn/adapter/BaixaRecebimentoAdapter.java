package net.inforgyn.adapter;

import java.util.Date;

import net.inforgyn.constante.SituacaoPagamentoEnum;
import net.inforgyn.model.BaixaRecebimento;
import net.inforgyn.model.Credito;
import net.inforgyn.util.UsuarioSessaoUtil;

public class BaixaRecebimentoAdapter {
	public static BaixaRecebimento gerarBaixa(Credito credito){
		BaixaRecebimento baixa = new BaixaRecebimento();
		baixa.setDataPagamento(new Date());
		baixa.setLancamento(new Date());
		baixa.setCredito(credito);
		if(credito.getSituacao().equals(SituacaoPagamentoEnum.PAG_PARCIAL)){
			baixa.setValor(credito.getValor().subtract(credito.getValorPago()));
		}else{
			baixa.setValor(credito.getValor());
		}
		baixa.setUsuario(UsuarioSessaoUtil.getUsuario());
		return baixa;
	}
}
