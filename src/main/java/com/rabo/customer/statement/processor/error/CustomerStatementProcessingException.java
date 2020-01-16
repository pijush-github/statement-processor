package com.rabo.customer.statement.processor.error;

public class CustomerStatementProcessingException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CustomerStatementProcessingException() {}

	public CustomerStatementProcessingException(String message) {
		super(message);
	}

	public CustomerStatementProcessingException(Throwable cause) {
		super(cause);
	}

	public CustomerStatementProcessingException(String message, Throwable cause) {
		super(message, cause);
	}

	public CustomerStatementProcessingException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
