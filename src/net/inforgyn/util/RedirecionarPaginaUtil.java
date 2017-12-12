package net.inforgyn.util;

import java.io.IOException;

import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class RedirecionarPaginaUtil {
	public static void redirect(String uri){
		try {
			String context = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
			String path = context+"/"+uri;
			path = path.replace("//", "/");
			FacesContext.getCurrentInstance().getExternalContext().redirect(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	private void redirectPretty(String path){
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();
			String contextPath = externalContext.getRequestContextPath();

			externalContext.redirect(contextPath+path);
			facesContext.responseComplete();
		} catch (IOException e) {
			throw new FacesException(e);
		}
	}
}
