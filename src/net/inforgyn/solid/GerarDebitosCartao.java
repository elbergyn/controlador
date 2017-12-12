package net.inforgyn.solid;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import net.inforgyn.constante.SituacaoPagamentoEnum;
import net.inforgyn.model.CartaoCredito;
import net.inforgyn.model.ContaPagar;
import net.inforgyn.model.Debito;
import net.inforgyn.model.DebitoCartao;
import net.inforgyn.util.DataUtil;

public class GerarDebitosCartao extends GerarDebito {

	public GerarDebitosCartao(ContaPagar conta, CartaoCredito cartao) {
		super(conta, cartao);
	}

	@Override
	public void gerar() {
		List<Debito> debitos = new LinkedList<Debito>();

		for (int i = 0; i < conta.getParcelaTotal(); i++) {
			DebitoCartao debito = new DebitoCartao();
			debito.setConta(conta);
			debito.setDescricao(conta.getDescricao());
			debito.setLancamento(conta.getDataLancamento());
			debito.setValor(conta.getValorParcelas().get(i).getValor().getBigDecimal());
			debito.setVencimento(DataUtil.adicionarMes(conta.getVencimento(), i * 1));
			debito.setUsuario(conta.getUsuario());
			debito.setTipoPagamento(conta.getTipoPagamento());
			debito.setCartao(cartao);
			debito.setParcela(i+1);
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
