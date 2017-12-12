package net.inforgyn.automatic;

import net.inforgyn.repository.ContaReceberDao;
import net.inforgyn.util.faces.CDIServiceLocator;

import org.apache.log4j.Logger;

/**
 * Atualizar status de contas a receber e a pagar
 */

public class AtualizarStatusContaReceber extends RotinasAutomaticas {
	//@Inject 
	private ContaReceberDao recebimentoDao;

	public AtualizarStatusContaReceber() {
		recebimentoDao = CDIServiceLocator.getBean(ContaReceberDao.class);
	}
	
	@Override
	public void run() {
		System.out.println("######Atualizar status das contas a receber vencidas");
		recebimentoDao.atualizarStatusContas();
		System.out.println("######Atualizou status das contas a receber vencidas");
		Logger.getLogger(this.getClass()).info("Atualizou status das contas a receber vencidas ");
	}
}
