package net.inforgyn.constante;

import javax.faces.context.FacesContext;

public enum ConstantesSistemaEnum {
	SISTEMA("Controlar"),
	DOMINIO("localhost:8080"), 
	PATH(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()),
	REMETENTE("controlar.inforgyn.net"),
	SUPORTE("elber_gyn@hotmail.com"),
	TEMPO_SESSAO(600);
	
	private String descricao = "";
	private Integer valor = 0 ;
	
	private ConstantesSistemaEnum(Integer valor){
		this.valor = valor;
	}
	
	public Integer getValor(){
		return this.valor;
	}
	
	private ConstantesSistemaEnum(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao(){
		return this.descricao;
	}
}
