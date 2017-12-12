package net.inforgyn.exception;

import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

import net.inforgyn.neg.LogSisNeg;
import net.inforgyn.util.RedirecionarPaginaUtil;
import net.inforgyn.util.faces.CDIServiceLocator;
import net.inforgyn.util.faces.FacesUtil;

public class JsfExceptionHandler extends ExceptionHandlerWrapper {

	private ExceptionHandler wrapped;
	//@Inject 
	private LogSisNeg logNeg;

	public JsfExceptionHandler(ExceptionHandler wrapped) {
		this.wrapped = wrapped;
		logNeg = CDIServiceLocator.getBean(LogSisNeg.class);
	}

	@Override
	public ExceptionHandler getWrapped() {
		return this.wrapped;
	}

	@Override
	public void handle() throws FacesException {
		
		Iterator<ExceptionQueuedEvent> events = getUnhandledExceptionQueuedEvents()
				.iterator();

		while (events.hasNext()) {
			ExceptionQueuedEvent event = events.next();
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event
					.getSource();

			Throwable exception = context.getException();
			
			boolean handled = false;
			
			NegocioException negocioException = null;
			DaoException daoException = null;
			PermissaoExclusaoException exclusaoException = null;
			CriptografiaException criptoException = null;
			InfoException infoException = null;
			ValorDuplicadoException valorDuplicadoException = null;
			
			negocioException = getNegocioException(exception);
			
			if(negocioException == null){
				daoException = getDaoException(exception);
			}
			
			if(daoException == null){
				exclusaoException = getPermissaoExclusaoException(exception);
			}
			
			if(exclusaoException == null){
				infoException = getInfoException(exception);
			}
			
			if(infoException == null){
				criptoException = getCriptografiaException(exception);
			}
			
			if(criptoException == null){
				valorDuplicadoException = getValorDuplicadoException(exception);
			}
			
			try {
				if (exception instanceof ViewExpiredException) {
					handled = true;
					redirect("/");
				} else if (negocioException != null) {
					handled = true;
					FacesUtil.alertMessageSimples(negocioException.getMessage());
					logNeg.salvar(negocioException);
				} else if (daoException != null) {
					handled = true;
					FacesUtil.errorMessageSimples(daoException.getMessage());
					logNeg.salvar(daoException);
				} else if (exclusaoException != null) {
					handled = true;
					FacesUtil.errorMessageSimples(exclusaoException.getMessage());
					logNeg.salvar(exclusaoException);
				} else if (criptoException != null) {
					handled = true;
					FacesUtil.errorMessageSimples(criptoException.getMessage());
					logNeg.salvar(criptoException);
				}else if(infoException != null){
					handled = true;
					FacesUtil.infoMessageSimples(infoException.getMessage());
				}else if(valorDuplicadoException != null){
					handled = true;
					FacesUtil.errorMessageSimples(valorDuplicadoException.getMessage());
				} else {
					logNeg.salvar(exception);
					redirect("/erro/");
				}
			} finally {
				if (handled) {
					events.remove();
				}
			}
		}

		getWrapped().handle();
	}
	
	private NegocioException getNegocioException(Throwable exception) {
		if (exception instanceof NegocioException) {
			return (NegocioException) exception;
		} else if (exception.getCause() != null) {
			return getNegocioException(exception.getCause());
		}
		
		return null;
	}
	
	private DaoException getDaoException(Throwable exception) {
		if (exception instanceof DaoException) {
			return (DaoException) exception;
		} else if (exception.getCause() != null) {
			return getDaoException(exception.getCause());
		}
		
		return null;
	}
	
	private InfoException getInfoException(Throwable exception) {
		if (exception instanceof InfoException) {
			return (InfoException) exception;
		} else if (exception.getCause() != null) {
			return getInfoException(exception.getCause());
		}
		
		return null;
	}
	
	private PermissaoExclusaoException getPermissaoExclusaoException(Throwable exception) {
		if (exception instanceof PermissaoExclusaoException) {
			return (PermissaoExclusaoException) exception;
		} else if (exception.getCause() != null) {
			return getPermissaoExclusaoException(exception.getCause());
		}
		
		return null;
	}
	
	private CriptografiaException getCriptografiaException(Throwable exception) {
		if (exception instanceof CriptografiaException) {
			return (CriptografiaException) exception;
		} else if (exception.getCause() != null) {
			return getCriptografiaException(exception.getCause());
		}
		
		return null;
	}
	
	private ValorDuplicadoException getValorDuplicadoException(Throwable exception) {
		if (exception instanceof ValorDuplicadoException) {
			return (ValorDuplicadoException) exception;
		} else if (exception.getCause() != null) {
			return getValorDuplicadoException(exception.getCause());
		}
		
		return null;
	}

	private void redirect(String page) {
		RedirecionarPaginaUtil.redirect(page);
	}

}