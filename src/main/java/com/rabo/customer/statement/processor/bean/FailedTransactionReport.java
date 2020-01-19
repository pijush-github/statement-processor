package com.rabo.customer.statement.processor.bean;

import java.util.List;

public class FailedTransactionReport {

	private List<FailedTransactionInfo> failedTransactions;
	private String noumberOfFailedTransactions;
	private String processedStatementMessage;
	private Long transactionProcessingReportId;
		
	/**
	 * @return the failedTransactions
	 */
	public List<FailedTransactionInfo> getFailedTransactions() {
		return failedTransactions;
	}
	/**
	 * @param failedTransactions the failedTransactions to set
	 */
	public void setFailedTransactions(List<FailedTransactionInfo> failedTransactions) {
		this.failedTransactions = failedTransactions;
	}
	/**
	 * @return the noumberOfFailedTransactions
	 */
	public String getNoumberOfFailedTransactions() {
		return noumberOfFailedTransactions;
	}
	/**
	 * @param noumberOfFailedTransactions the noumberOfFailedTransactions to set
	 */
	public void setNoumberOfFailedTransactions(String noumberOfFailedTransactions) {
		this.noumberOfFailedTransactions = noumberOfFailedTransactions;
	}
	/**
	 * @return the processedStatementMessage
	 */
	public String getProcessedStatementMessage() {
		return processedStatementMessage;
	}
	/**
	 * @param processedStatementMessage the processedStatementMessage to set
	 */
	public void setProcessedStatementMessage(String processedStatementMessage) {
		this.processedStatementMessage = processedStatementMessage;
	}
	/**
	 * @return the transactionProcessingReportId
	 */
	public Long getTransactionProcessingReportId() {
		return transactionProcessingReportId;
	}
	/**
	 * @param transactionProcessingReportId the transactionProcessingReportId to set
	 */
	public void setTransactionProcessingReportId(Long transactionProcessingReportId) {
		this.transactionProcessingReportId = transactionProcessingReportId;
	}
}
