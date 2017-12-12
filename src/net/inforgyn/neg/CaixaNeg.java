package net.inforgyn.neg;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import net.inforgyn.adapter.CaixaMovimentoAdapter;
import net.inforgyn.constante.TipoLancamentoCaixaEnum;
import net.inforgyn.model.Caixa;
import net.inforgyn.model.CaixaFechamento;
import net.inforgyn.model.CaixaMovimento;
import net.inforgyn.model.TipoRecebimento;
import net.inforgyn.persistence.Transactional;
import net.inforgyn.repository.CaixaDao;
import net.inforgyn.util.NumericUtil;
import net.inforgyn.util.StringUtil;
import net.inforgyn.util.UsuarioSessaoUtil;

public class CaixaNeg {
	@Inject	private CaixaDao dao;
	@Inject private CaixaMovimentoNeg movimentoNeg;
	//@Inject private TipoRecebimentoNeg recebimentoNeg;
	@Inject private List<TipoRecebimento> tiposRecebimento;

	@Transactional
	public Caixa salvar(Caixa caixa) {
		TipoRecebimento recebimento = null;
		for(TipoRecebimento tr : tiposRecebimento){
			if(tr.getDescricao().equals("Dinheiro")){
				recebimento = tr;
				break;
			}
		}
		
		caixa = (Caixa) dao.salvar(caixa);
		CaixaMovimento movimento = CaixaMovimentoAdapter.gerarMovimentoAbertura(caixa, recebimento);
		dao.salvar(movimento);
		
		return caixa;
	}
	
	public Caixa alterar(Caixa caixa){
		return (Caixa)dao.alterar(caixa);
	}

	@Produces
	public List<Caixa> caixasAbertos() {
		return dao.caixasAbertos(UsuarioSessaoUtil.getUsuario());
	}

	@Transactional
	public void caixaParcial(List<Caixa> caixas) {
		for (Caixa caixa : caixas) {
			BigDecimal total = new BigDecimal(0);
			total = total.add(caixa.getValorInicial());

			if (caixa.getId() != null) {
				caixa = dao.carregarMovimentos(caixa);
			}
			if (caixa.getMovimentos() != null) {
				caixa.setValorFinal(movimentoNeg.calcularSaldo(caixa.getMovimentos()));
			}
		}
	}

	public boolean possuiCaixa(Date data) {
		return dao.possuiCaixa(data);
	}

	public List<CaixaFechamento> calcularFechamento(Caixa caixa) {
		/*caixa = dao.carregarMovimentos(caixa);
		Map<String, String> totais = new HashMap<String, String>();
		
		for(TipoRecebimento tr : tiposRecebimento){
			BigDecimal saldo = new BigDecimal(0);
			for(CaixaMovimento cm : caixa.getMovimentos()){
				if(cm.getTipoRecebimento().equals(tr)){
					saldo = saldo.add(cm.getValor());
				}
			}
			if(saldo.longValue() > 0){
				totais.put(tr.getDescricao(), NumericUtil.moeda(saldo));
			}
		}
		return totais;*/
		List<CaixaFechamento> totais = dao.calcularFechamento(caixa);
		return totais;
	}
}
