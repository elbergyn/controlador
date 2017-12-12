package net.inforgyn.filter;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Filtro para configurar apache para que não realize conversões
 * para 0 quando não houver valor informado em variáveis do tipo
 * númerico
 * **/

@WebListener
public class AppContextListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.setProperty("org.apache.el.parser.COERSE_TO_ZERO", "false");
	}
	
}
