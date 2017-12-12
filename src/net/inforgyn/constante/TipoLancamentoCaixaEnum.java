package net.inforgyn.constante;

public enum TipoLancamentoCaixaEnum {
	ENTRADA("Entrada"), 
	SAIDA("Saída"),
	ABERTURA("Abertura"),
	FECHAMENTO("Fechamento"),
	AJUSTE("Ajuste");

	private String descricao;

	private TipoLancamentoCaixaEnum(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
