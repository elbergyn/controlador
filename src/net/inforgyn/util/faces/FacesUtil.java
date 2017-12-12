package net.inforgyn.util.faces;

import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

public class FacesUtil {
	public static boolean isPostBack(){
		return FacesContext.getCurrentInstance().isPostback();
	}
	
	public static boolean isNotPostBack(){
		return !isPostBack();
	}
	
	public static void infoMessageSimples(String mensagem){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, mensagem));
	}
	
	public static void errorMessageSimples(String mensagem){
		FacesContext.getCurrentInstance().addMessage("messageSimple", new FacesMessage(FacesMessage.SEVERITY_ERROR, null, mensagem));
	}
	
	public static void alertMessageSimples(String mensagem){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, mensagem));
	}
	
	public static void fatalMessageSimples(String mensagem){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, null, mensagem));
	}
	
	public static void alertMessage(String titulo, String mensagem){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, titulo, mensagem));
	}
	
	public static void alertMessage(String id,String titulo, String mensagem){
		FacesContext.getCurrentInstance().addMessage(id, new FacesMessage(FacesMessage.SEVERITY_WARN, titulo, mensagem));
	}
	
	public static void abrirDialog(String dialog, Map<String,Object> options,
			Map<String,List<String>> params){
		RequestContext.getCurrentInstance().openDialog("/gestao/dialogFramework/"+dialog, options, null);
	}
	
	public static void messageInfoDialog(String titulo, String mensagem){
		FacesMessage fc = new FacesMessage(FacesMessage.SEVERITY_INFO, titulo, mensagem);
		RequestContext.getCurrentInstance().showMessageInDialog(fc);
	}
	
	public static void messageAlertDialog(String titulo, String mensagem){
		FacesMessage fc = new FacesMessage(FacesMessage.SEVERITY_WARN, titulo, mensagem);
		RequestContext.getCurrentInstance().showMessageInDialog(fc);
	}
	
	public static void messageErrorDialog(String titulo, String mensagem){
		FacesMessage fc = new FacesMessage(FacesMessage.SEVERITY_ERROR, titulo, mensagem);
		RequestContext.getCurrentInstance().showMessageInDialog(fc);
	}
}
