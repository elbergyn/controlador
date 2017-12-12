package net.inforgyn.exception;

import javax.persistence.PersistenceException;

public class PermissaoExclusaoException extends PersistenceException {

	private static final long serialVersionUID = 1L;

	public PermissaoExclusaoException(String message) {
		super(message);
	}
	
}
