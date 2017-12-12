package net.inforgyn.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.inforgyn.model.Usuario;
import net.inforgyn.neg.UsuarioNeg;
import net.inforgyn.util.UsuarioSessaoUtil;
import net.inforgyn.util.faces.FacesUtil;

@Named
@ViewScoped
public class DadosUsuarioBean implements CadastroBean, Serializable {

	private static final long serialVersionUID = 1L;
	@Inject	private Usuario usuario;
	@Inject	private UsuarioNeg usuarioNeg;

	public boolean getSolicitarSenha(){
		return false;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	@PostConstruct
	public void init() {
		usuario = UsuarioSessaoUtil.getUsuario();
		usuario.setSenha(null);
	}

	@Override
	public void novo() {
	}

	@Override
	public void salvar() {
		if(usuario != null){
			usuarioNeg.alterar(usuario);
			FacesUtil.infoMessageSimples("Dados alterados para usuário: "+this.usuario.getDescricao());
		}
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
