package net.inforgyn.model;

import net.inforgyn.impl.MensagemImpl;

public class Sms implements MensagemImpl{
	private String celular;
	private String conteudo;
	private String remetente;
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	public String getConteudo() {
		return conteudo;
	}
	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
	public String getRemetente() {
		return remetente;
	}
	public void setRemetente(String remetente) {
		this.remetente = remetente;
	}
}
