package net.inforgyn.constante;

public enum TipoRecebimentoEnum {
	DINHEIRO("Dinheiro"), CHEQUE("Cheque");
	
	private final String descricao;
	
	private TipoRecebimentoEnum(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao(){
		return this.descricao;
	}
	
}
