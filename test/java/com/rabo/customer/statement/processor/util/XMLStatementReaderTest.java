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
public class XMLStatementReaderTest {

	@InjectMocks
	XMLStatementReader xmlStatementReader;
	
	private InputStream isXML;
	
	@Before
    public void setup() {
        this.isXML = this.getClass().getClassLoader().getResourceAsStream("records.xml");
    }
	
	@Test
	public void testReadXMLFile() throws Exception {
		List<TransactionRecord> txnRecords = xmlStatementReader.read(isXML);
		Assert.assertEquals(2, txnRecords.size());
	}

}
