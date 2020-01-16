package com.rabo.customer.statement.processor.util;

import java.io.InputStream;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.rabo.customer.statement.processor.bean.TransactionRecord;

@RunWith(MockitoJUnitRunner.class)
public class CSVStatementReaderTest {

	@InjectMocks
	CSVStatementReader csvStatementReader;
	
	 private InputStream isCSV;
	
	@Before
    public void setup() {
        this.isCSV = this.getClass().getClassLoader().getResourceAsStream("records.csv");
    }
	
	@Test
	public void testReadCSVFile() throws Exception {
		List<TransactionRecord> txnRecords = csvStatementReader.read(isCSV);
		Assert.assertEquals(10, txnRecords.size());
	}

}
