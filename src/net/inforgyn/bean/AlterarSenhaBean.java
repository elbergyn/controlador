package net.inforgyn.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.inforgyn.model.Usuario;
import net.inforgyn.neg.UsuarioNeg;
import net.inforgyn.util.Criptografar;
import net.inforgyn.util.UsuarioSessaoUtil;
import net.inforgyn.util.faces.FacesUtil;

@Named
@ViewScoped
public class AlterarSenhaBean implements CadastroBean, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String novaSenha;
	private String senhaAtual;
	private Usuario usuario;
	@Inject
	private UsuarioNeg usuarioNeg;

	public String getNovaSenha() {
		return novaSenha;
	}

	public String getSenhaAtual() {
		return senhaAtual;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	@PostConstruct
	public void init() {
		usuario = UsuarioSessaoUtil.getUsuario();
	}

	@Override
	public void novo() {
	}

	@Override
	public void salvar() {
		if (validarSenhaAtual()) {
			usuario.setSenha(novaSenha);
			usuarioNeg.alterar(usuario);
			FacesUtil.infoMessageSimples("Senha alterada");
		}
	}

	public boolean validarSenhaAtual() {
		if (!Criptografar.codificar(senhaAtual).equals(usuario.getSenha())) {
			FacesUtil.alertMessage("", "Senha atual não confere");
			return false;
		}
		return true;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
