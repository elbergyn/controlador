package net.inforgyn.neg;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import net.inforgyn.constante.PeriodoPagamentoEnum;
import net.inforgyn.constante.SituacaoPagamentoEnum;
import net.inforgyn.constante.TipoRecebimentoEnum;
import net.inforgyn.exception.NegocioException;
import net.inforgyn.model.BaixaRecebimento;
import net.inforgyn.model.ContaReceber;
import net.inforgyn.model.Credito;
import net.inforgyn.model.Parcela;
import net.inforgyn.repository.ContaReceberDao;
import net.inforgyn.solid.GerarCredito;
import net.inforgyn.solid.Money;
import net.inforgyn.util.DataUtil;
import net.inforgyn.util.UsuarioSessaoUtil;

public class ContaReceberNeg {
	@Inject
	private ContaReceberDao dao;

	public ContaReceber alterar(ContaReceber conta){
		if(DataUtil.dataAnterior(conta.getVencimento(), DataUtil.getDataAtual())){
			conta.setSituacao(SituacaoPagamentoEnum.ATRASO);
		}else{
			conta.setSituacao(SituacaoPagamentoEnum.A_VENCER);
		}
		if(conta.getTipoRecebimento().equals(TipoRecebimentoEnum.CHEQUE)){
			validarParcelasCheque(conta);
		}
		new GerarCredito(conta).gerar();
		dao.alterar(conta);
		return conta;
	}
	
	public ContaReceber salvar(ContaReceber conta){
		if(DataUtil.dataAnterior(conta.getVencimento(), DataUtil.getDataAtual())){
			conta.setSituacao(SituacaoPagamentoEnum.ATRASO);
		}else{
			conta.setSituacao(SituacaoPagamentoEnum.A_VENCER);
		}		
		if(conta.getTipoRecebimento().equals(TipoRecebimentoEnum.CHEQUE)){
			validarParcelasCheque(conta);
		}
		conta.setUsuario(UsuarioSessaoUtil.getUsuario());
		new GerarCredito(conta).gerar();
		dao.salvar(conta);
		return conta;
	}
	
	public void calcularParcelamento(ContaReceber conta) {
		conta.setParcelas(new ArrayList<Parcela>());
		if (conta.getValorTotal() != null) {
			Money[] valores = Money.valueOf(conta.getValorTotal()).dividir(
					conta.getParcelaTotal());
			Date[] vencimentos = new Date[conta.getParcelaTotal()];

			vencimentos[0] = conta.getVencimento();
			if (conta.getParcelaTotal() > 1) {
				if (conta.getPeriodoPagamento().equals(
						PeriodoPagamentoEnum.TRINTA)) {
					for (int i = 1; i < conta.getParcelaTotal(); i++) {
						vencimentos[i] = DataUtil.adicionarMes(
								conta.getVencimento(), i * 1);
					}
				} else {
					Date vencimento = conta.getVencimento();
					for (int i = 1; i < conta.getParcelaTotal(); i++) {
						vencimento = DataUtil.adicionarDias(vencimento,
								conta.getPeriodoPagamento());
						vencimentos[i] = vencimento;
					}
				}
			}

			for (int i = 0; i < vencimentos.length; i++) {
				conta.getValorParcelas().add(
						new Parcela(i+1, valores[i], vencimentos[i]));
			}
		}
	}

	public ContaReceber carregarParcelamento(ContaReceber conta) {
		conta = carregarCreditos(conta);
		conta.setValorParcelas(new LinkedList<Parcela>());
		int i = 0;
		for(Credito c : conta.getCreditos()){
			conta.getValorParcelas().add(new Parcela(++i, Money.valueOf(c.getValor()), c.getVencimento()));
		}
		return conta;
	}
	
	public ContaReceber carregarCreditos(ContaReceber conta) {
		return dao.carregarCreditos(conta);
	}
	
	public void excluir(ContaReceber conta) {
		dao.excluir(conta);
	}

	public List<ContaReceber> listarContas() {
		return dao.listarPorUsuarioComPai(ContaReceber.class, UsuarioSessaoUtil.getUsuario());
	}
	
	@Produces
	public List<ContaReceber> listarContasAbertas(){
		return dao.listarEmAberto();
	}

	private void validarParcelasCheque(ContaReceber conta){
		if(conta.getNumerosCheque().size() != conta.getParcelaTotal()){
			throw new NegocioException("Adicione os cheques de acordo com o número de parcelas");
		}
	}
	
	public void atualizarStatusContas(List<BaixaRecebimento> baixas) {
		dao.atualizarStatusContas(baixas);
	}

}
