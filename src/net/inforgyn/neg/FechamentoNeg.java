package net.inforgyn.neg;

import java.util.List;

import javax.inject.Inject;

import net.inforgyn.model.CaixaFechamento;
import net.inforgyn.repository.FechamentoDao;

public class FechamentoNeg {
	@Inject private FechamentoDao dao;
	
	public void salvar(List<CaixaFechamento> fechamento){
		dao.salvar(fechamento);
	}
}
