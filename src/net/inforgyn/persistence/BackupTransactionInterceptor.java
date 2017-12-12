package net.inforgyn.persistence;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.jboss.weld.context.ContextNotActiveException;

@Interceptor
@Transactional
public class BackupTransactionInterceptor implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private @Inject EntityManager manager;	
	
	@AroundInvoke
	public Object invoke(InvocationContext context) throws Exception{
		EntityTransaction trx=null;
		try{
			trx = manager.getTransaction();
		}catch(ContextNotActiveException e){
			manager = new JPAEntityManager().getEntityManager();
			trx = manager.getTransaction();
		}
		
		boolean criador = false;
		try{
			if(!trx.isActive()){
				//fazer rollback no que já passou
				trx.begin();
				trx.rollback();
				
				//inicia a transação
				trx.begin();
				criador = true;
			}
			return context.proceed();
		}catch(Exception e){
			if(trx != null && criador){
				trx.rollback();
			}
			e.printStackTrace();
			throw e;
		}finally{
			if (trx !=null && trx.isActive() && criador){
				trx.commit();
			}
		}
	}

}
