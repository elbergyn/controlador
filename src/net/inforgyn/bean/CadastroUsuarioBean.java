package net.inforgyn.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.inforgyn.constante.TipoUsuarioEnum;
import net.inforgyn.model.Usuario;
import net.inforgyn.neg.UsuarioNeg;
import net.inforgyn.util.DataUtil;
import net.inforgyn.util.UsuarioSessaoUtil;
import net.inforgyn.util.faces.FacesUtil;

@Named
@ViewScoped
public class CadastroUsuarioBean implements CadastroBean, Serializable {

	private static final long serialVersionUID = 1L;
	private boolean solicitarSenha;
	@Inject
	private Usuario usuario;
	@Inject
	private UsuarioNeg usuarioNeg;
	@Inject
	private List<Usuario> usuarios;

	public void excluir() {

		usuarioNeg.excluir(usuario);
		usuarios.remove(usuario);

		novo();

	}
	
	public boolean getMostrarAdm(){
		return UsuarioSessaoUtil.getUsuario().getTipo().equals(TipoUsuarioEnum.ADM);
	}

	public boolean getSolicitarSenha() {
		return solicitarSenha;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	@PostConstruct
	public void init() {
		usuario.setTipo(TipoUsuarioEnum.COMUM);
		solicitarSenha = true;
	}

	public List<Usuario> listarUsuarios() {
		return usuarios;
	}

	@Override
	public void novo() {
		usuario = new Usuario();
	}

	@Override
	public void salvar() {
		if (usuario.getId() == null) {
			usuario.setValidade(DataUtil.adicionarDias(new Date(), 60));
			usuario = usuarioNeg.salvar(usuario);
			usuarios.add(usuario);
			FacesUtil.infoMessageSimples("Usuário cadastrado: "
					+ this.usuario.getDescricao());
			solicitarSenha = true;
		} else {
			usuarioNeg.alterar(usuario);
			solicitarSenha = true;
			FacesUtil.infoMessageSimples("Usuário alterado: "
					+ this.usuario.getDescricao());
		}
		novo();
	}

	public SelectItem[] getTipos() {
		return usuarioNeg.getTiposUsuario();
	}

	public void setUsuario(Usuario usuario) {
		solicitarSenha = false;
		this.usuario = usuario;
	}
}
