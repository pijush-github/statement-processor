package com.rabo.customer.statement.processor.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.rabo.customer.statement.processor.bean.TransactionRecord;

public class CSVStatementReader implements StatementReader {

	private static final Logger LOGGER = LoggerFactory.getLogger(CSVStatementReader.class);
	
	@Override
	public List<TransactionRecord> read(final InputStream inFileInput) throws Exception  {
		List<TransactionRecord> theTransactionRecords = new ArrayList<>();
		try {
			BufferedReader theBufferedReader = new BufferedReader(new InputStreamReader(inFileInput,"UTF-8"));
			CSVReader theCSVReader = new CSVReaderBuilder(theBufferedReader).withSkipLines(1).build();
			List<String[]> theCSVFileData =  theCSVReader.readAll();
			for(String[] theRow : theCSVFileData) {
				theTransactionRecords.add(prepareRecord(theRow));
			}
		
		} catch (final IOException ex) {
			LOGGER.error("Issue with parser configuration or file parsing. Original error is :", ex);
			throw new Exception(ex);
		}
		return theTransactionRecords;
	}

	private TransactionRecord prepareRecord(final String[] inRow) {
		TransactionRecord theRecord = new TransactionRecord();
		theRecord.setReference(Long.valueOf(inRow[0].trim()));
		theRecord.setAccountNumber(inRow[1]);
		theRecord.setDescription(inRow[2]);
		theRecord.setStartBalance(new BigDecimal(inRow[3]).setScale(2));
		theRecord.setMutation(new BigDecimal(inRow[4]).setScale(2));
		theRecord.setEndBalance(new BigDecimal(inRow[5]).setScale(2));
		return theRecord;
	}
	
}
