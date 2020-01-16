package com.rabo.customer.statement.processor.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.rabo.customer.statement.processor.bean.TransactionRecord;

public class MonthlyStatementParser {

	private Map<String, StatementReader> readers = new HashMap<>();
	
	public void registerFileReader(final StatementReader inReader, final String inExtension) {
		readers.put(inExtension, inReader);
	}

	public Iterator<TransactionRecord> parse(final InputStream inputStream, final String inFileNameExtension) throws Exception {
		StatementReader theFileReader = readers.get(inFileNameExtension);
		if(theFileReader == null) {
			throw new Exception(String.format("No file reader is rgistered for %s files.", inFileNameExtension));
		}
		return theFileReader.read(inputStream).iterator();
	}

}
