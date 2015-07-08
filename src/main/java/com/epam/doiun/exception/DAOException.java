package com.epam.doiun.exception;

public class DAOException extends RuntimeException {

	private static final long serialVersionUID = -8137976011028304057L;

	public DAOException() {
		super();
	}

	public DAOException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	public DAOException(String message) {
		super(message);
	}

	public DAOException(Throwable cause) {
		super(cause);
	}
}
