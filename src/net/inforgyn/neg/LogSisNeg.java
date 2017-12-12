package net.inforgyn.neg;

import java.util.List;

import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import net.inforgyn.constante.ConstantesSistemaEnum;
import net.inforgyn.impl.Alerta;
import net.inforgyn.model.Email;
import net.inforgyn.model.LogSisErro;
import net.inforgyn.model.filterPesquisa.FilterLog;
import net.inforgyn.repository.LogDao;
import net.inforgyn.solid.EnviarMensagemBackground;
import net.inforgyn.solid.MensagemEmail;
import net.inforgyn.util.DataUtil;
import net.inforgyn.util.UsuarioSessaoUtil;

public class LogSisNeg {
	@Inject
	private LogDao dao;
	@Inject
	private LogSisErro log;

	private LogSisErro log(Throwable exception){
		StringBuilder sb = new StringBuilder();
		sb.append("<h3>Log de erro</h3>");
		sb.append("<br>");
		if(UsuarioSessaoUtil.getUsuario() != null){
		sb.append("Usuário_id: ").append(UsuarioSessaoUtil.getUsuario().getId());
		}
		sb.append("<br>");
		if(UsuarioSessaoUtil.getUsuario() != null){
			sb.append("Usuário: ").append(UsuarioSessaoUtil.getUsuario().getDescricao());
			sb.append("<br>");
		}
		sb.append("Hora: ").append(DataUtil.getDataAtualCompletaString());
		sb.append("<br></br>");
		if(exception.getCause() != null){
			sb.append("Causa: ").append(exception.getCause().getMessage());
		}
		for (StackTraceElement s : exception.getStackTrace()) {
			if (s.getClassName().contains("Dao")) {
				sb.append("<br>").append(s.toString());
			}
		}
		
		LogSisErro log = new LogSisErro();
		log.setMensagem(sb.toString());
		if(UsuarioSessaoUtil.getUsuario() != null){
			log.setUsuarioId(UsuarioSessaoUtil.getUsuario().getId());
			log.setUsuarioDesc(UsuarioSessaoUtil.getUsuario().getDescricao());
		}
		
		return log;
	}
	
	@Produces
	public List<LogSisErro> logs(){
		return logs(null);
	}
	
	public List<LogSisErro> logs(FilterLog filtro){
		return dao.listar(filtro);
	}
	
	public void salvar(Throwable exception) {
		if (exception != null) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) facesContext
					.getExternalContext().getSession(true);
			
			LogSisErro log = log(exception);
			session.setAttribute("ERRO", log.getMensagem());
			
			Email me = new Email();
			me.setAssunto("Erro no sistema "+ConstantesSistemaEnum.SISTEMA.getDescricao());
			me.setDestinatario(ConstantesSistemaEnum.SUPORTE.getDescricao());
			me.setMensagem(log.getMensagem());
			me.setRemetente(ConstantesSistemaEnum.REMETENTE.getDescricao());
			
			Alerta alerta = new MensagemEmail(me);
			new EnviarMensagemBackground(alerta).enviar();
			
			dao.salvar(log);
		}
	}
	
	public void excluir(List<LogSisErro> logs){
		dao.excluir(logs);
	}
}
