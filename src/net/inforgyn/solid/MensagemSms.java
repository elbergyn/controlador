package net.inforgyn.solid;

import net.inforgyn.impl.Alerta;
import net.inforgyn.model.Sms;

public class MensagemSms extends Alerta{
	private Sms sms;

	public MensagemSms(Sms sms) {
		super();
		this.sms = sms;
	}
	
	public void enviar(){
		System.out.println("Enviado sms para: "+sms.getCelular());		
	};
}
