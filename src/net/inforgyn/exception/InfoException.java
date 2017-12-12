package net.inforgyn.exception;

import javax.persistence.PersistenceException;

public class InfoException extends PersistenceException {

	private static final long serialVersionUID = 1L;

	public InfoException(String message) {
		super(message);
	}
	
}
