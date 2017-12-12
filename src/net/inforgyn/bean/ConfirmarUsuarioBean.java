package net.inforgyn.bean;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.inforgyn.constante.ConstantesSistemaEnum;
import net.inforgyn.model.Usuario;
import net.inforgyn.neg.UsuarioNeg;
import net.inforgyn.util.RedirecionarPaginaUtil;
import net.inforgyn.util.UsuarioSessaoUtil;
import net.inforgyn.util.faces.FacesUtil;

import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLMapping;

@Named
@RequestScoped
@URLMapping(id = "confirmarUsuario", pattern = "/confirmar_usuario/#{confirmarUsuarioBean.descricao}/#{confirmarUsuarioBean.senha}", viewId = "/gestao/controle/confirmarUsuario.xhtml")
public class ConfirmarUsuarioBean {
	private String descricao;
	private String senha;
	@Inject
	private UsuarioNeg usuarioNeg;
	@Inject
	LoginBean loginBean;

	@URLAction
	public String confirmarUsuario() {
		Boolean confirmado = usuarioNeg.confirmarUsuario(descricao, senha);
		if (confirmado) {
			Usuario usuario = new Usuario();
			usuario.setDescricao(descricao);
			usuario.setSenha(senha);

			usuario = usuarioNeg.logar(usuario);
			UsuarioSessaoUtil.addUsuario(usuario);
			return "pretty:home";
		} else {
			return "";
		}
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
