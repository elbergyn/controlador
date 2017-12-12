package net.inforgyn.constante;


public enum TipoLancamentoEnum {
	ENTRADA("Entrada"), SAIDA("Saída");

	private String descricao;

	private TipoLancamentoEnum(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
}
