package net.inforgyn.automatic;

import org.apache.log4j.Logger;

import net.inforgyn.repository.ContaPagarDao;
import net.inforgyn.util.faces.CDIServiceLocator;



/**
 Atualizar status de contas a receber e a pagar
 */
public class AtualizarStatusContaPagar extends RotinasAutomaticas {
	//@Inject
	private ContaPagarDao contaDao;

	public AtualizarStatusContaPagar() {
		contaDao = CDIServiceLocator.getBean(ContaPagarDao.class);
	}

	@Override
	public void run() {
		System.out.println("######Atualizar status das contas a pagar vencidas");
		contaDao.atualizarStatusContas();
		System.out.println("######Atualizou status das contas a pagar vencidas");
		Logger.getLogger(this.getClass()).info("Atualizou status das contas a pagar vencidas ");
	}

}
