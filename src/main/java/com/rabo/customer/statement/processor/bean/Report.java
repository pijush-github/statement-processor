package com.rabo.customer.statement.processor.bean;

public class Report {

	private String id;
	private String reportName;
	
	public Report(String id, String reportName) {
		super();
		this.id = id;
		this.reportName = reportName;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the reportName
	 */
	public String getReportName() {
		return reportName;
	}
	/**
	 * @param reportName the reportName to set
	 */
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
}
