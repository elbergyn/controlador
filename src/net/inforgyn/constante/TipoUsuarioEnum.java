package net.inforgyn.constante;

public enum TipoUsuarioEnum {
	ADM("Administrador"), COMUM("Comum");
	
	private final String descricao;
	
	private TipoUsuarioEnum(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao(){
		return this.descricao;
	}
}
