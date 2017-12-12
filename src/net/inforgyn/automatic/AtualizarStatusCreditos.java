package net.inforgyn.automatic;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import net.inforgyn.repository.BaixaPagamentoDao;
import net.inforgyn.util.faces.CDIServiceLocator;

import org.apache.log4j.Logger;

/**
 Atualizar status de contas a receber e a pagar
 */

public class AtualizarStatusCreditos extends RotinasAutomaticas {
	//@Inject
	private BaixaPagamentoDao pagamentoDao;

	public AtualizarStatusCreditos() {
		pagamentoDao = CDIServiceLocator.getBean(BaixaPagamentoDao.class);
	}

	@Override
	public void run() {
		System.out.println("######Atualizar status dos creditos vencidos ");
		pagamentoDao.atualizarSituacao();
		System.out.println("######Atualizou status dos creditos vencidos ");
		Logger.getLogger(this.getClass()).info("Atualizou status dos creditos vencidos ");
	}

}
