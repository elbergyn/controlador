package net.inforgyn.constante;

public enum SituacaoPagamentoEnum {
	A_VENCER("À vencer"), 
	QUITADO("Quitado"),
	ATRASO("Em atraso"),
	PAG_PARCIAL("Pagamento parcial");
	
	private final String descricao;
	
	private SituacaoPagamentoEnum(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao(){
		return this.descricao;
	}
}
