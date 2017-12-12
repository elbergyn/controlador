package net.inforgyn.util;

import net.inforgyn.constante.ConstantesSistemaEnum;
import net.inforgyn.model.Email;
import net.inforgyn.model.Usuario;

public class PadraoEmailUtil {
	
	public static Email confirmarCadastro(Usuario usuario) {
		StringBuilder conteudo = new StringBuilder();
		conteudo.append("Para confirmar o cadastro acesse o link ");
		conteudo.append("<a href='");
		conteudo.append(ConstantesSistemaEnum.DOMINIO.getDescricao());
		conteudo.append(ConstantesSistemaEnum.PATH.getDescricao());
		conteudo.append("/confirmar_usuario/");
		conteudo.append(usuario.getDescricao());
		conteudo.append("/");
		conteudo.append(usuario.getSenha()).append("'>");
		conteudo.append("Recuperar senha");
		conteudo.append("</a>");
		
		String mensagem = layout();
		mensagem = mensagem.replace("#conteudo#", conteudo.toString());
		
		Email email = new Email();
		email.setAssunto("Confirmar cadastro");
		email.setMensagem(conteudo.toString());
		email.setDestinatario(usuario.getEmail());
		email.setRemetente(ConstantesSistemaEnum.REMETENTE.getDescricao());
		
		return email; 
	}
	
	private static String layout(){
		StringBuilder sb = new StringBuilder();
		sb.append("<b>Montar cabeçalho</b>");
		sb.append("#conteudo#");
		sb.append("<b>Montar rodape</b>");
		return sb.toString();
	}

	public static Email recurarSenha(Usuario usuario){
		StringBuilder conteudo = new StringBuilder();
		conteudo.append("Acesse o link ");
		conteudo.append("<a href='");
		conteudo.append(ConstantesSistemaEnum.DOMINIO.getDescricao());
		conteudo.append(ConstantesSistemaEnum.PATH.getDescricao());
		conteudo.append("/recriar_senha/");
		conteudo.append(usuario.getDescricao());
		conteudo.append("/");
		conteudo.append(usuario.getSenha()).append("'>");
		conteudo.append("Recuperar senha");
		conteudo.append("</a>");
		
		String mensagem = layout();
		mensagem.replace("#conteudo#", conteudo.toString());
		
		Email email = new Email();
		email.setAssunto("Recuperar senha");
		email.setMensagem(conteudo.toString());
		email.setDestinatario(usuario.getEmail());
		email.setRemetente(ConstantesSistemaEnum.REMETENTE.getDescricao());
		
		return email; 
	}
}
