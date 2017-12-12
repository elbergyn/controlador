package net.inforgyn.neg;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import net.inforgyn.exception.NegocioException;
import net.inforgyn.model.BaixaPagamento;
import net.inforgyn.model.BaixaRecebimento;
import net.inforgyn.model.ContaReceber;
import net.inforgyn.model.Credito;
import net.inforgyn.model.EmitirCheque;
import net.inforgyn.persistence.JPAPersistenceUtil;
import net.inforgyn.repository.CreditoDao;
import net.inforgyn.util.UsuarioSessaoUtil;

public class CreditoNeg {
	@Inject
	private CreditoDao dao;
	
	public Credito alterar(Credito credito) {
		dao.alterar(credito);
		return credito;
	}

	public Credito carregarRecebimentos(Credito credito) {
		return (Credito) dao.carregarLazy(credito, "recebimentos");
	}

	public List<BaixaRecebimento> listarRecebimentos(Credito credito) {
		return dao.listarRecebimentos(credito);
	}
	
	public void excluir(Credito credito){
			dao.excluir(credito);
	}
	
	public List<ContaReceber> listarCreditos(Map<String, Object> filtro) {
		return dao.listarCreditos(filtro);
	}
	
	@Produces
	public List<Credito> listarCreditosAbertos() {
		return dao.listarCreditosAbertos(null);
	}

	public List<Credito> listarCreditosAbertos(Map<String, Object> filtro) {
		return dao.listarCreditosAbertos(filtro);
	}

	public Credito pesquisarPorId(Class classe, Long id){
		return (Credito) dao.pesquisarPorId(classe, id);
	}
	
	public Credito salvar(Credito debito){
		debito.setUsuario(UsuarioSessaoUtil.getUsuario());
		dao.salvar(debito);
		return debito;
	}

	public void atualizarStatusDebitos(List<BaixaRecebimento> baixas) {
		dao.atualizarStatusCreditos(baixas);
	}

	public Map<String, BigDecimal> valorTotalPesquisa(Map<String, Object> filtro) {
		return dao.valorTotalPesquisa(filtro);
	}

	
}
