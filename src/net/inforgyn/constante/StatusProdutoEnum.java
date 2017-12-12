package net.inforgyn.constante;

public enum StatusProdutoEnum {
	ATIVO("Ativo"), 
	INATIVO("Inativo");
		
	private String descricao;
		
	private StatusProdutoEnum(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
