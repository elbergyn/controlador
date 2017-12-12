package net.inforgyn.neg;

import java.util.List;

import javax.inject.Inject;

import net.inforgyn.model.Caixa;
import net.inforgyn.model.CaixaFechamento;
import net.inforgyn.model.filterPesquisa.FilterCaixaFechamento;
import net.inforgyn.repository.CaixaFechamentoDao;

public class CaixaFechamentoNeg {
	@Inject	private CaixaFechamentoDao dao;

	public List<Caixa> pesquisar(FilterCaixaFechamento filtro) {
		return dao.pesquisar(filtro);
	}
	
	public List<CaixaFechamento> listarFechamentos(Caixa caixa){
		return dao.listarFechamentos(caixa);
	}
}
