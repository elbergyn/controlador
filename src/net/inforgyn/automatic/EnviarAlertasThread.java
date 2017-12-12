package net.inforgyn.automatic;

import java.util.ArrayList;
import java.util.List;

import net.inforgyn.impl.Alerta;

public class EnviarAlertasThread extends RotinasAutomaticas {
	private List<Alerta> alertas = new ArrayList<Alerta>();
	
	public EnviarAlertasThread(List<Alerta> alertas) {
		this.alertas = alertas;
	}
	
	public EnviarAlertasThread(Alerta alerta) {
		this.alertas.add(alerta);
	}

	@Override
	public void run() {
		for(Alerta a : alertas){
			a.enviar();
		}
	}
}
