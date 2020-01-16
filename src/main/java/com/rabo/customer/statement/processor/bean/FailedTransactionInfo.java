package com.rabo.customer.statement.processor.bean;

public class FailedTransactionInfo {

	private Long transactionReference;
	
	private String transactionDescription;
	
	/**
	 * @return the transactionReference
	 */
	public Long getTransactionReference() {
		return transactionReference;
	}

	/**
	 * @param transactionReference the transactionReference to set
	 */
	public void setTransactionReference(Long transactionReference) {
		this.transactionReference = transactionReference;
	}

	/**
	 * @return the transactionDescription
	 */
	public String getTransactionDescription() {
		return transactionDescription;
	}

	/**
	 * @param transactionDescription the transactionDescription to set
	 */
	public void setTransactionDescription(String transactionDescription) {
		this.transactionDescription = transactionDescription;
	}

	
}
