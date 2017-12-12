package net.inforgyn.constante;

public enum StatusVendaEnum {
	ORCAMENTO("Orcamento"),
	CANCELADO("Cancelado"),
	CONCLUIDO("Concluido");
	
	private String descricao;
	
	private StatusVendaEnum(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao(){
		return descricao;
	}
}
