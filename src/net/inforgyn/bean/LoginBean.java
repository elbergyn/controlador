package net.inforgyn.bean;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.inforgyn.automatic.AtualizarStatusContaPagar;
import net.inforgyn.automatic.AtualizarStatusContaReceber;
import net.inforgyn.automatic.AtualizarStatusCreditos;
import net.inforgyn.automatic.AtualizarStatusDebitos;
import net.inforgyn.model.Usuario;
import net.inforgyn.neg.UsuarioNeg;
import net.inforgyn.util.DataUtil;
import net.inforgyn.util.RedirecionarPaginaUtil;
import net.inforgyn.util.UsuarioSessaoUtil;
import net.inforgyn.util.faces.FacesUtil;

@Named
@ViewScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject private Usuario usuario;
	@Inject	private UsuarioNeg neg;
	
	public LoginBean() {
	}

	public void logar() {
		usuario = neg.logar(usuario);

		if(usuario != null && DataUtil.dataAnterior(usuario.getValidade(), DataUtil.getDataAtual())){
			FacesUtil.alertMessageSimples("Usuário expirado, entre em contato com desenvolvedor");
		}else if(usuario != null && usuario.getConfirmado()){
			UsuarioSessaoUtil.addUsuario(usuario);
			
			new AtualizarStatusContaReceber().run();
			new AtualizarStatusContaPagar().run();
			new AtualizarStatusCreditos().run();
			new AtualizarStatusDebitos().run();
			
			RedirecionarPaginaUtil.redirect("/home/");
		}else if(usuario != null && !usuario.getConfirmado()){
			FacesUtil.alertMessage("erro","Erro de login","Primeiro realize a confirmação de usuário através de seu e-mail para realizar o acesso");
		}else{
			usuario = new Usuario();
			FacesUtil.alertMessage("erro","Erro de login","Usuário ou senha incorreto");
		}
		//return "";
	}
	
	public void logoff(){
		System.out.println("logoff");
		UsuarioSessaoUtil.logoff();
		RedirecionarPaginaUtil.redirect("/");
	}
	
	public void novoUsuario(){
		RedirecionarPaginaUtil.redirect("/novo_usuario/");
	}
	
	public String getNomeUsuarioLogado(){
		if(UsuarioSessaoUtil.getUsuario() == null){
			RedirecionarPaginaUtil.redirect("/");
			return "";
		}
		
		String periodo = "";
		if(DataUtil.getHora() < 12){
			periodo = "Bom dia, ";
		}else if(DataUtil.getHora() < 18){
			periodo = "Boa tarde, ";
		}else{
			periodo = "Boa noite, ";
		}
		return periodo+UsuarioSessaoUtil.getUsuario().getDescricao();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
