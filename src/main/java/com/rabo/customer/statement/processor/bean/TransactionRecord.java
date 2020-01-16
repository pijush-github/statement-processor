package com.rabo.customer.statement.processor.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "record")
public class TransactionRecord implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	private Long reference;
	
	private String accountNumber;
	private String description;
	private BigDecimal startBalance;
	private BigDecimal mutation;
	private BigDecimal endBalance;

	
	public TransactionRecord(){}
    public TransactionRecord(Long reference, String accountNumber, String description, 
    		BigDecimal startBalance, BigDecimal mutation, BigDecimal endBalance) {
        this.reference = reference;
        this.accountNumber = accountNumber;
        this.description = description;
        this.startBalance = startBalance;
        this.mutation = mutation;
        this.endBalance = endBalance;
    }
    
	/**
	 * @return the reference
	 */
	public Long getReference() {
		return reference;
	}


	/**
	 * @param reference the reference to set
	 */
	@XmlAttribute
	public void setReference(Long reference) {
		this.reference = reference;
	}


	/**
	 * @return the accountNumber
	 */
	public String getAccountNumber() {
		return accountNumber;
	}


	/**
	 * @param accountNumber the accountNumber to set
	 */
	@XmlElement
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}


	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * @param description the description to set
	 */
	@XmlElement
	public void setDescription(String description) {
		this.description = description;
	}


	/**
	 * @return the startBalance
	 */
	public BigDecimal getStartBalance() {
		return startBalance;
	}


	/**
	 * @param startBalance the startBalance to set
	 */
	@XmlElement
	public void setStartBalance(BigDecimal startBalance) {
		this.startBalance = startBalance;
	}


	/**
	 * @return the mutation
	 */
	public BigDecimal getMutation() {
		return mutation;
	}


	/**
	 * @param mutation the mutation to set
	 */
	@XmlElement
	public void setMutation(BigDecimal mutation) {
		this.mutation = mutation;
	}


	/**
	 * @return the endBalance
	 */
	public BigDecimal getEndBalance() {
		return endBalance;
	}


	/**
	 * @param endBalance the endBalance to set
	 */
	@XmlElement
	public void setEndBalance(BigDecimal endBalance) {
		this.endBalance = endBalance;
	}
	
	@Override
    public String toString() { 
        return String.format("{reference: %s, accountNumber: %s, description: %s, startBalance: %s, mutation: %s, endBalance: %s}",
        		this.reference,this.accountNumber,this.description,this.startBalance,this.mutation,this.endBalance); 
    } 
}
