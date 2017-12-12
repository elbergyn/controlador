package net.inforgyn.constante;

public enum TipoPagamentoEnum {
	DINHEIRO("Dinheiro"), CHEQUE("Cheque"), CARTAO("Cartão");
	
	private final String descricao;
	
	private TipoPagamentoEnum(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao(){
		return this.descricao;
	}
	
}
