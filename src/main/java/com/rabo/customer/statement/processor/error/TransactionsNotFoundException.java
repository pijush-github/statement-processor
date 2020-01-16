package com.rabo.customer.statement.processor.error;

public class TransactionsNotFoundException extends CustomerStatementProcessingException {

	private static final long serialVersionUID = 1L;

	public TransactionsNotFoundException(String message) {
		super(message);
	}

}
