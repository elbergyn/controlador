package net.inforgyn.neg;

import java.util.List;

import javax.enterprise.inject.Produces;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import net.inforgyn.constante.TipoUsuarioEnum;
import net.inforgyn.model.Usuario;
import net.inforgyn.persistence.Transactional;
import net.inforgyn.repository.UsuarioDao;
import net.inforgyn.solid.MensagemEmail;
import net.inforgyn.solid.EnviarMensagemBackground;
import net.inforgyn.util.Criptografar;
import net.inforgyn.util.PadraoEmailUtil;
import net.inforgyn.util.UsuarioSessaoUtil;

public class UsuarioNeg {
	@Inject private UsuarioDao dao;
	@Inject private ParametrosNeg paramentrosNeg;
	@Inject private CategoriaDespesaNeg categoriaNeg;
	@Inject private TipoRecebimentoNeg tipoNeg;
	
	public Usuario alterar(Usuario usuario) {
		if(usuario.getSenha() == null || usuario.getSenha().isEmpty()){
			dao.alterarSemSenha(usuario);
		}else{
			usuario.setSenha(Criptografar.codificar(usuario.getSenha()));			
			dao.alterar(usuario);
		}
		return usuario;
	}
	
	public SelectItem[] getTiposUsuario(){
		SelectItem[] itens = new SelectItem[TipoUsuarioEnum.values().length];
		int i = 0;
		for (TipoUsuarioEnum e : TipoUsuarioEnum.values()) {
			itens[i++] = new SelectItem(e, e.getDescricao());
		}
		return itens;
	}

	@Transactional
	public Usuario salvar(Usuario usuario){
		
		usuario.setSenha(Criptografar.codificar(usuario.getSenha()));
		if(!UsuarioSessaoUtil.getUsuario().getId().equals(1L)){
			usuario.setUsuarioPai(UsuarioSessaoUtil.getUsuario());			
		}
		usuario.setParametros(paramentrosNeg.gerarParametrosPadrao(usuario));
		usuario = (Usuario)dao.salvar(usuario);
		
		categoriaNeg.gerarCategoriasPadrao(usuario);
		tipoNeg.gerarTipoRecebimentoPadrao(usuario);
		
		enviarEmailConfirmacao(usuario);
		
		return usuario;
	}
	
	public Usuario localizarUsuario(String email) {
		return dao.localizarUsuario(email);
	}
	
	public void enviarEmailRecuperacao(Usuario usuario){
		MensagemEmail alerta = new MensagemEmail(PadraoEmailUtil.recurarSenha(usuario));
		
		new EnviarMensagemBackground(alerta).enviar();
	}
	
	private void enviarEmailConfirmacao(Usuario usuario){		
		MensagemEmail alerta = new MensagemEmail(PadraoEmailUtil.confirmarCadastro(usuario));
		
		new EnviarMensagemBackground(alerta).enviar();
	}

	public void excluir(Usuario usuario){
			dao.excluir(usuario);
	}
	
	public Usuario pesquisarPorId(Long id){
		return (Usuario) dao.pesquisarPorId(Usuario.class, id);
	}

	@Produces
	public List<Usuario> listarUsuarios() {
		return dao.listar();
	}

	public Usuario logar(Usuario usuario) {		
		return dao.logar(usuario);
	}

	public Usuario pesquisarPorUsuarioSenha(String descricao, String senhaAtual) {
		return dao.pesquisarPorUsuarioSenha(descricao, senhaAtual);
	}

	public Boolean confirmarUsuario(String descricao, String senha) {
		return dao.confirmarUsuario(descricao, senha);
	}


}
