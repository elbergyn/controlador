package net.inforgyn.constante;

public enum StatusCaixaEnum {
	
	ABERTO("Aberto"), FECHADO("Fechado");
	
	private String descricao;
	
	private StatusCaixaEnum(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
