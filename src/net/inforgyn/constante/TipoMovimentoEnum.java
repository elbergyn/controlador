package net.inforgyn.constante;

public enum TipoMovimentoEnum {
	CREDITO("Crédito"), DEBITO("Débito");
	
	private final String descricao;
	
	private TipoMovimentoEnum(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao(){
		return this.descricao;
	}
	
}
