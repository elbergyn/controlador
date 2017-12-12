package net.inforgyn.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.inforgyn.model.Usuario;
import net.inforgyn.neg.UsuarioNeg;
import net.inforgyn.util.faces.FacesUtil;

import org.primefaces.context.RequestContext;

@Named
@ViewScoped
public class RecuperarSenhaBean implements CadastroBean, Serializable{
	private static final long serialVersionUID = 1L;
	
	private String email;
	@Inject private Usuario usuario;
	@Inject private UsuarioNeg usuarioNeg;

	@Override
	public void novo() {
	}

	@Override
	public void salvar() {
	}
	
	public void localizarUsuario(){
		usuario = usuarioNeg.localizarUsuario(email);
		if(usuario == null){
			FacesUtil.errorMessageSimples("Não localizado usuário para o e-mail informado");
		}else{
			usuarioNeg.enviarEmailRecuperacao(usuario);
			/*RequestContext.getCurrentInstance().openDialog("dlgRecuperar");*/
			RequestContext.getCurrentInstance().showMessageInDialog(new
					FacesMessage(FacesMessage.SEVERITY_INFO,
					"Recuperar senha", "<b>E-mail encaminhado</b></br></br>" +
							"Caso não esteja na caixa de entrada " +
							"verifique na caixa de spam."));
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
