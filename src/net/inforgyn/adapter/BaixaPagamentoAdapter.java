package net.inforgyn.adapter;

import java.util.Date;

import net.inforgyn.constante.SituacaoPagamentoEnum;
import net.inforgyn.model.BaixaPagamento;
import net.inforgyn.model.Debito;
import net.inforgyn.util.UsuarioSessaoUtil;

public class BaixaPagamentoAdapter {
	public static BaixaPagamento gerarBaixa(Debito debito){
		BaixaPagamento baixa = new BaixaPagamento();
		baixa.setDataPagamento(new Date());
		baixa.setLancamento(new Date());
		baixa.setDebito(debito);
		if(debito.getSituacao().equals(SituacaoPagamentoEnum.PAG_PARCIAL)){
			baixa.setValor(debito.getValor().subtract(debito.getValorPago()));
		}else{
			baixa.setValor(debito.getValor());
		}
		baixa.setUsuario(UsuarioSessaoUtil.getUsuario());
		return baixa;
	}
}
