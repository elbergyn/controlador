package net.inforgyn.filter;

import javax.enterprise.context.RequestScoped;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import net.inforgyn.automatic.RotinasAutomaticas;
import net.inforgyn.persistence.JPAEntityManager;

@RequestScoped
public class ServletListener extends JPAEntityManager implements ServletContextListener {
		
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
		/*JPAEntityManager.factory = Persistence.createEntityManagerFactory("rotina_backup_post_db");
		
		RotinasAutomaticas ra = new RotinasAutomaticas();
		ra.atualizarStatusContas();
		new Thread(ra).start();
*/
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("Contexto está sendo destruido....");
		//JPAEntityManager.finalizar(getEntityManager());
	}
}
