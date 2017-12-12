package net.inforgyn.bean;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.inforgyn.model.Usuario;
import net.inforgyn.neg.UsuarioNeg;
import net.inforgyn.util.RedirecionarPaginaUtil;
import net.inforgyn.util.UsuarioSessaoUtil;
import net.inforgyn.util.faces.FacesUtil;

@Named
@ViewScoped
public class RecriarSenhaBean implements CadastroBean {

	private String novaSenha;
	private String senhaAtual;
	private String descricao;
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

	@Override
	public void novo() {
	}

	@Override
	public void salvar() {
		usuario.setSenha(novaSenha);
		usuarioNeg.alterar(usuario);
		UsuarioSessaoUtil.addUsuario(usuario);
		RedirecionarPaginaUtil.redirect("/home/");
	}

	public void carregarUsuarioRecadastrarSenha() {
		if (descricao != null && senhaAtual != null) {
			usuario = usuarioNeg.pesquisarPorUsuarioSenha(descricao, senhaAtual);
		}

		if (usuario == null) {
			FacesUtil.infoMessageSimples("Usuário não identificado");
			
			RedirecionarPaginaUtil.redirect("/");
		}
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
}
