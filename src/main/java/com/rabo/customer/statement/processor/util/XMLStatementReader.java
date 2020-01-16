package com.rabo.customer.statement.processor.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabo.customer.statement.processor.bean.TransactionRecord;
import com.rabo.customer.statement.processor.bean.TransactionRecords;

public class XMLStatementReader implements StatementReader {

	private static final Logger LOGGER = LoggerFactory.getLogger(XMLStatementReader.class);
	
	@Override
	public List<TransactionRecord> read(final InputStream inFileInput) throws Exception {
		List<TransactionRecord> theTransactionRecords = new ArrayList<>();
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(TransactionRecords.class);
			 
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			TransactionRecords theStatementRecord = (TransactionRecords) jaxbUnmarshaller.unmarshal(inFileInput);
			Iterator<TransactionRecord> theTxnIterator = theStatementRecord.getRecordList().iterator();
			while(theTxnIterator.hasNext())
			{
				TransactionRecord theTxnRecord = (TransactionRecord) theTxnIterator.next();
				theTxnRecord.getStartBalance().setScale(2);
				theTxnRecord.getMutation().setScale(2);
				theTxnRecord.getEndBalance().setScale(2);
				theTransactionRecords.add(theTxnRecord);			
			}
		} catch (final JAXBException ex) {
			LOGGER.error("Issue with parser configuration or file parsing. Original error is :", ex);
			throw new Exception(ex);
		}
		return theTransactionRecords;
	}

}
