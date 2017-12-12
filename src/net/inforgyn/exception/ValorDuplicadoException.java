package net.inforgyn.exception;

import javax.persistence.PersistenceException;

public class ValorDuplicadoException extends PersistenceException {

	private static final long serialVersionUID = 1L;
	
	public ValorDuplicadoException(Exception e, String message) {
		super(message, e);
	}
	
}
