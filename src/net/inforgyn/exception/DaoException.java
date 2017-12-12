package net.inforgyn.exception;

import javax.persistence.PersistenceException;

public class DaoException extends PersistenceException {

	private static final long serialVersionUID = 1L;
	
	public DaoException(Exception e, String message) {
		super(message, e);
	}
	
}
