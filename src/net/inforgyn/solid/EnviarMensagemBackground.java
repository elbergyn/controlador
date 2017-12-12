package net.inforgyn.solid;

import java.util.ArrayList;
import java.util.List;

import net.inforgyn.automatic.EnviarAlertasThread;
import net.inforgyn.automatic.RotinasAutomaticas;
import net.inforgyn.impl.Alerta;

public class EnviarMensagemBackground {
	public List<Alerta> alertas = new ArrayList<Alerta>();
	
	public EnviarMensagemBackground(List<Alerta> alertas) {
		this.alertas = alertas;
	}
	
	public EnviarMensagemBackground(Alerta alerta) {
		alertas.add(alerta);
	}
	
	public void enviar(){
		RotinasAutomaticas ra = new EnviarAlertasThread(alertas);
		Thread thread = new Thread(ra);
		thread.start();
	}
}
