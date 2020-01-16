package com.rabo.customer.statement.processor.error;

public class UnrecognizedStatementTypeException extends CustomerStatementProcessingException {

	private static final long serialVersionUID = 1L;

	public UnrecognizedStatementTypeException(String message) {
		super(message);
	}

}
