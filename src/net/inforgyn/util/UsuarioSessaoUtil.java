package net.inforgyn.util;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.inforgyn.constante.ConstantesSistemaEnum;
import net.inforgyn.model.Usuario;

public class UsuarioSessaoUtil {
	public static void addUsuario(Usuario usuario){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext()
				.getSession(true);
		// tempo de sessão
		session.setMaxInactiveInterval(ConstantesSistemaEnum.TEMPO_SESSAO.getValor());
		session.setAttribute("usuarioLogado", usuario);
				
		//((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false)).setAttribute("usuarioLogado", usuario);
	}
	
	public static Usuario getUsuario(){
		HttpSession session = ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false));
		Usuario usuario = null;
		if(session != null){
			usuario = (Usuario) session.getAttribute("usuarioLogado");
		}
		return usuario;
	}
	
	public static void logoff(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext()
				.getSession(true);
		session.setAttribute("usuarioLogado", null);
	}
}
