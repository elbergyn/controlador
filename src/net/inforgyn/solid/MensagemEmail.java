package net.inforgyn.solid;

import net.inforgyn.impl.Alerta;
import net.inforgyn.model.Email;

public class MensagemEmail extends Alerta{
	private Email email;
	
	public MensagemEmail(Email email) {
		this.email = email;
	}

	public void enviar(){
		System.out.println("Enviado email para: "+email.getDestinatario());	
		System.out.println("mensagem: ");
		System.out.println(email.getMensagem());
	};
}
