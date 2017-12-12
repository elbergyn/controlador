package net.inforgyn.solid;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import net.inforgyn.constante.SituacaoPagamentoEnum;
import net.inforgyn.model.ContaReceber;
import net.inforgyn.model.Credito;
import net.inforgyn.model.CreditoCheque;
import net.inforgyn.util.DataUtil;

public class GerarCreditoCheque extends GerarCredito {
	
	public GerarCreditoCheque(ContaReceber conta) {
		super(conta);
	}

	@Override
	public void gerar() {
		List<Credito> creditos = new LinkedList<Credito>();
		
		List<Long> numeros = new ArrayList<Long>(conta.getNumerosCheque()); 

		for (int i = 0; i < conta.getParcelaTotal(); i++) {
			CreditoCheque credito = new CreditoCheque();
			credito.setConta(conta);
			credito.setDescricao(conta.getDescricao());
			credito.setLancamento(conta.getDataLancamento());
			credito.setUsuario(conta.getUsuario());
			credito.setDevedor(conta.getDevedor());
			credito.setNumeroCheque(numeros.get(i));
			credito.setTipoRecebimento(conta.getTipoRecebimento());
			credito.setParcelaTotal(conta.getParcelaTotal());
			credito.setVencimento(conta.getValorParcelas().get(i).getVencimento());
			credito.setValor(conta.getValorParcelas().get(i).getValor().getBigDecimal());
			credito.setParcela(conta.getValorParcelas().get(i).getNumero());
			if(DataUtil.dataAnterior(credito.getVencimento(), DataUtil.getDataAtual())){
				credito.setSituacao(SituacaoPagamentoEnum.ATRASO);
			}else{
				credito.setSituacao(SituacaoPagamentoEnum.A_VENCER);
			}
			creditos.add(credito);
		}
		conta.setCreditos(creditos);
	}
}
