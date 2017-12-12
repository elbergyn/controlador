package net.inforgyn.repository;

import java.util.List;

import net.inforgyn.model.Caixa;
import net.inforgyn.model.CaixaMovimento;

public class CaixaMovimentoDao extends DaoModel {

	public List<CaixaMovimento> carregarMovimentos(Caixa caixa) {
		caixa = (Caixa) persistence.carregarLazy(caixa, "movimentos");
		return caixa.getMovimentos();
	}

}