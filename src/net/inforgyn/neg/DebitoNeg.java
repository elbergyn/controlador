package net.inforgyn.neg;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import net.inforgyn.exception.NegocioException;
import net.inforgyn.model.BaixaPagamento;
import net.inforgyn.model.Debito;
import net.inforgyn.model.DebitoCheque;
import net.inforgyn.model.EmitirCheque;
import net.inforgyn.model.filterPesquisa.FilterDebito;
import net.inforgyn.model.filterPesquisa.FiltroBaixaPagamento;
import net.inforgyn.repository.DebitoDao;
import net.inforgyn.util.UsuarioSessaoUtil;

public class DebitoNeg {
	@Inject
	private DebitoDao dao;
	
	public Debito alterar(Debito debito) {
		dao.alterar(debito);
		return debito;
	}

	public Debito carregarPagamentos(Debito debito) {
		return (Debito) dao.carregarLazy(debito, "pagamentos", "categoria");
	}
	
	public List<BaixaPagamento> carregarPagamentos(List<Debito> debitos) {
		return dao.carregarLazy(debitos, "pagamentos");
	}

	public void excluir(Debito debito){
			dao.excluir(debito);
	}

	public List<DebitoCheque> listarChequesEmitidos(FilterDebito filtro) {
		return dao.listarChequesEmitidos(filtro);
	}
	
	public List<Debito> listarDebitos(FilterDebito filtro) {
		return dao.listarDebitos(filtro);
	}
	
	@Produces
	public List<Debito> listarDebitosAbertos() {
		return dao.listarDebitosAbertos(null);
	}
	
	public List<Debito> listarDebitosAbertos(FiltroBaixaPagamento filtro) {
		return dao.listarDebitosAbertos(filtro);
	}

	public Debito pesquisarPorId(Class classe, Long id){
		return (Debito) dao.pesquisarPorId(classe, id);
	}

	public Debito salvar(Debito debito){
		debito.setUsuario(UsuarioSessaoUtil.getUsuario());
		dao.salvar(debito);
		return debito;
	}

	public boolean varificarChequeEmitido(EmitirCheque cheque) {
		Boolean existente = dao.varificarChequeEmitido(cheque);
		if(existente){
			throw new NegocioException("Este número não é válido: Este cheque já foi utilizado em outra conta");
		}		
		return existente;
	}

	public void atualizarStatusDebitos(List<BaixaPagamento> baixas) {
		dao.atualizarStatusDebitos(baixas);
	}

	public Map<String, BigDecimal> valorTotalPesquisa(FilterDebito filtro) {
		return dao.valorTotalPesquisa(filtro);
	}
}
