package net.inforgyn.neg;

import java.util.Calendar;
import java.util.List;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import net.inforgyn.model.ValorProduto;
import net.inforgyn.persistence.Transactional;
import net.inforgyn.repository.ValorProdutoDao;

public class ValorProdutoNeg {
	@Inject private ValorProdutoDao dao;
	@Inject private HistoricoValorProdutoNeg historicoNeg;
	
	public void excluir(ValorProduto valor){
		dao.excluir(valor);
	}
	
	@Produces
	public List<ValorProduto> listarValorProdutos(){
		return dao.listrarValores();
	}
	
	public void salvar(ValorProduto valor){
		gerarHistorico(valor);
		valor.setData(Calendar.getInstance());
		dao.alterar(valor);
	}
	
	public void gerarHistorico(ValorProduto valor){
		historicoNeg.gerarHistorico(valor);
	}
	
	@Transactional
	public void atualizar(ValorProduto valor){
		historicoNeg.gerarHistorico(valor);
		valor.setData(Calendar.getInstance());
		dao.alterar(valor);		
	}

	public ValorProduto pesquisarPorIdProduto(Long id) {
		return dao.pesquisarPorIdProduto(id);
	}
}
