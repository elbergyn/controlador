package net.inforgyn.solid;

import java.util.LinkedList;
import java.util.List;

import net.inforgyn.constante.SituacaoPagamentoEnum;
import net.inforgyn.model.ContaPagar;
import net.inforgyn.model.Debito;
import net.inforgyn.model.DebitoDinheiro;
import net.inforgyn.util.DataUtil;

public class GerarDebitosDinheiro extends GerarDebito {
		
	public GerarDebitosDinheiro(ContaPagar conta) {
		super(conta);
	}

	@Override
	public void gerar() {
		List<Debito> debitos = new LinkedList<Debito>();

		for (int i = 0; i < conta.getParcelaTotal(); i++) {
			DebitoDinheiro debito = new DebitoDinheiro();
			debito.setConta(conta);
			debito.setDescricao(conta.getDescricao());
			debito.setLancamento(conta.getDataLancamento());
			debito.setUsuario(conta.getUsuario());
			debito.setValor(conta.getValorParcelas().get(i).getValor().getBigDecimal());
			debito.setParcela(conta.getValorParcelas().get(i).getNumero());
			debito.setParcelaTotal(conta.getParcelaTotal());
			debito.setCategoria(conta.getCategoria());
			debito.setTipoPagamento(conta.getTipoPagamento());
			debito.setVencimento(conta.getValorParcelas().get(i).getVencimento());
			if(DataUtil.dataAnterior(debito.getVencimento(), DataUtil.getDataAtual())){
				debito.setSituacao(SituacaoPagamentoEnum.ATRASO);
			}else{
				debito.setSituacao(SituacaoPagamentoEnum.A_VENCER);
			}
			debitos.add(debito);
		}
		conta.setDebitos(debitos);
	}
}
