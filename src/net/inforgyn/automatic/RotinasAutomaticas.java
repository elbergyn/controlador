package net.inforgyn.automatic;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.junit.experimental.theories.Theory;

import net.inforgyn.persistence.Transactional;
import net.inforgyn.repository.DaoModel;


@RequestScoped
public class RotinasAutomaticas implements Runnable {
	private Integer hora;
	private Integer minuto;

	@Override
	public void run() {
		System.out.println("rotinas automáticas programadas");
		while (true) {
			hora = GregorianCalendar.getInstance().get(Calendar.HOUR_OF_DAY);
			minuto = GregorianCalendar.getInstance().get(Calendar.MINUTE);
			Integer segundo = GregorianCalendar.getInstance().get(Calendar.SECOND);

			// executadas a meia noite
			if (hora == 0 && minuto == 0) {
				atualizarStatusContas();
			}

			// atualizar de 60 em 60 segundos
			try {
				Thread.sleep(60 * 1000);
				//System.out.println("thred as "+hora+":"+minuto+":"+segundo);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Transactional
	public void atualizarStatusContas(){
		System.out.println("#####################################################");
		System.out.println("#############Atualizar contas########################");
		System.out.println("#####################################################");
		iniciar(new AtualizarStatusContaReceber());
		iniciar(new AtualizarStatusContaPagar());
		iniciar(new AtualizarStatusCreditos());
		iniciar(new AtualizarStatusDebitos());
		System.out.println("#####################################################");
		System.out.println("#############Contas atualizadas######################");
		System.out.println("#####################################################");
	}

	private void iniciar(Runnable rotina) {
		new Thread(rotina).start();
	}
}
