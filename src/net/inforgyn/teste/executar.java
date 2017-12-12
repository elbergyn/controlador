package net.inforgyn.teste;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class executar {

	public static void main(String[] args) {
		
		UsuarioTeste usuario = new UsuarioTeste();
		usuario.setNome("Elber");
		
		ClienteTeste cliente = new ClienteTeste();
		cliente.setNome("Cliente");
		
		List<Telefone> telefones = new ArrayList<Telefone>();
		Telefone fone1 = new Telefone();
		fone1.setNumero("111111111");
		fone1.setUsuarioTeste(usuario);
		
		Telefone fone2 = new Telefone();
		fone2.setNumero("222222222");
		fone2.setUsuarioTeste(usuario);
		
		telefones.add(fone1);
		telefones.add(fone2);
		
		cliente.setTelefones(telefones);
		EntityManager em = null;
		EntityTransaction trx = null;
		try{
			EntityManagerFactory factory = Persistence.createEntityManagerFactory("controlador_db");
			em = factory.createEntityManager();
			trx = em.getTransaction();
			trx.begin();
			em.persist(usuario);
			em.flush();
			em.persist(cliente);
			em.flush();
			trx.commit();
		}catch(Exception e){
			e.printStackTrace();
			trx.rollback();
		}finally {
			em.close();
		}
	}

}
