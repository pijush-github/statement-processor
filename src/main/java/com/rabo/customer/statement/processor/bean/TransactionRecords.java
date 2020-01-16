package com.rabo.customer.statement.processor.bean;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="records")
@XmlAccessorType(XmlAccessType.FIELD)
public class TransactionRecords implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<TransactionRecord> record;

	public TransactionRecords() {}

	/**
	 * @return the record
	 */
	public List<TransactionRecord> getRecordList() {
		return record;
	}

	/**
	 * @param record the record to set
	 */
	public void setRecordList(List<TransactionRecord> record) {
		this.record = record;
	}

	

}
