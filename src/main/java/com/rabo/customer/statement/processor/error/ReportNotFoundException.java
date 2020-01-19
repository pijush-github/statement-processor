package com.rabo.customer.statement.processor.error;

public class ReportNotFoundException extends CustomerStatementProcessingException {

	private static final long serialVersionUID = 1L;

	public ReportNotFoundException(String message) {
		super(message);
	}
}
