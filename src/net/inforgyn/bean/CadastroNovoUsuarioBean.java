package net.inforgyn.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.inforgyn.constante.TipoUsuarioEnum;
import net.inforgyn.model.Usuario;
import net.inforgyn.neg.CategoriaDespesaNeg;
import net.inforgyn.neg.UsuarioNeg;
import net.inforgyn.util.faces.FacesUtil;

@Named
@ViewScoped
public class CadastroNovoUsuarioBean implements CadastroBean, Serializable {

	private static final long serialVersionUID = 1L;
	@Inject	private Usuario usuario;
	@Inject	private UsuarioNeg usuarioNeg;
	
	
	public Usuario getUsuario() {
		return usuario;
	}

	@PostConstruct
	public void init() {
	}

	@Override
	public void novo() {
		usuario = new Usuario();
	}

	@Override
	public void salvar() {
		usuarioNeg.salvar(usuario);
		FacesUtil.infoMessageSimples("Usuário cadastrado: "+this.usuario.getDescricao()+", acesse seu e-mail para validar o acesso");
	}
	
	public boolean getSolicitarSenha(){
		return true;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
