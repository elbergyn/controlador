package net.inforgyn.constante;

public enum MessageUtilEnum {
	ERRO_PERSISTENCIA_ALTERAR("Ocorreu erro em persistência ao alterar dados"),
	ERRO_PERSISTENCIA_EXCLUIR("Ocorreu erro em persistência ao excluir dados"),
	ERRO_PERSISTENCIA_EXCLUIR_CONSTRAINT("Não é possivel excluir este item, o mesmo possui vínculos no sistema"),
	ERRO_PERSISTENCIA_LISTAR("Ocorreu erro em persistência ao listar dados"),
	ERRO_PERSISTENCIA_PESQUISAR("Ocorreu erro em persistência ao pesquisar dados"),
	ERRO_PERSISTENCIA_SALVAR_CONSTRAINT("Ocorreu erro em persistência ao salvar dados valor duplicado não permitido"),
	ERRO_PERSISTENCIA_SALVAR("Ocorreu erro em persistência ao salvar dados");
	
	private final String mensagem;
	
	private MessageUtilEnum(String mensagem){
		this.mensagem = mensagem;
	}
	
	public String getMensagem(){
		return this.mensagem;
	}
}
