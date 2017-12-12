package net.inforgyn.persistence;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;

@ApplicationScoped
public class JPAEntityManager {

	public static EntityManagerFactory factory = null;
	private static final Logger logger = Logger.getLogger(JPAEntityManager.class);
	public JPAEntityManager() {
		System.out.println("#####################################################");
		logger.info("#####################################################");
		System.out.println("###############Iniciando persistence#################");
		logger.info("###############Iniciando persistence#################");
		factory = Persistence.createEntityManagerFactory("controlador_db");
		System.out.println("###############persistence iniciado##################");
		logger.info("#####################################################");
		System.out.println("###############persistence iniciado##################");
	}

	@Produces @RequestScoped
	public static EntityManager getEntityManager() {
		return factory.createEntityManager();
	}

	public static void closeEntityManager(@Disposes EntityManager manager) {
		manager.close();
	}
}
