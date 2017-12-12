package net.inforgyn.model;

import net.inforgyn.impl.MensagemImpl;

public class Email implements MensagemImpl{
	private String assunto;
	private String mensagem;
	private String copia;
	private String destinatario;
	private String remetente;
	public String getAssunto() {
		return assunto;
	}
	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public String getCopia() {
		return copia;
	}
	public void setCopia(String copia) {
		this.copia = copia;
	}
	public String getDestinatario() {
		return destinatario;
	}
	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}
	public String getRemetente() {
		return remetente;
	}
	public void setRemetente(String remetente) {
		this.remetente = remetente;
	}
	
	
}
