package com.rabo.customer.statement.processor.service;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.multipart.MultipartFile;

import com.rabo.customer.statement.processor.bean.FailedTransactionReport;
import com.rabo.customer.statement.processor.config.StatementProcessorConfigurer;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = StatementProcessorConfigurer.class)
@TestPropertySource("classpath:messages.properties")
public class StatementProcessorServiceTest {
	
	@Autowired
    private StatementProcessorService statementService;
	
	private MultipartFile csvMultiFile;
    
    private MultipartFile xmlMultiFile;
	
	@Before
    public void setup() throws IOException {
		csvMultiFile = new MockMultipartFile("records","records.csv", "text/csv", this.getClass().getClassLoader().getResourceAsStream("records.csv"));
		xmlMultiFile = new MockMultipartFile("records","records.xml", "text/xml", this.getClass().getClassLoader().getResourceAsStream("records.xml"));
    }

	@Test
	public void testProcessCSVStatement() throws IOException, Exception {
		FailedTransactionReport txnReport = statementService.processStatement(csvMultiFile);
		Assert.assertNotNull(txnReport);
		Assert.assertEquals(2, txnReport.getFailedTransactions().size());
	}
	
	@Test
	public void testProcessXMLStatement() throws IOException, Exception {
		FailedTransactionReport txnReport = statementService.processStatement(xmlMultiFile);
		Assert.assertNotNull(txnReport);
		Assert.assertEquals(1, txnReport.getFailedTransactions().size());
	}

}
