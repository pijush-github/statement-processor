package com.rabo.customer.statement.processor.rest;

import java.util.Arrays;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rabo.customer.statement.processor.bean.FailedTransactionReport;
import com.rabo.customer.statement.processor.error.TransactionsNotFoundException;
import com.rabo.customer.statement.processor.error.CustomerStatementProcessingException;
import com.rabo.customer.statement.processor.error.UnrecognizedStatementTypeException;
import com.rabo.customer.statement.processor.service.StatementProcessorService;

@RestController
public class StatementProcessingController {
	
	@Value("${file.types.supported}")
	private String filetTypesSupported;
	
	@Autowired
	private StatementProcessorService statmentService;

	@RequestMapping(value = "/monthly/statement", headers = ("content-type=multipart/*"), method = RequestMethod.POST)
	public ResponseEntity<FailedTransactionReport> processMonthlyTransactions(@RequestParam("customerStatement") MultipartFile inputStatement) {
		HttpHeaders headers = new HttpHeaders();
		if (!Arrays.asList(filetTypesSupported.split(",")).contains(FilenameUtils.getExtension(inputStatement.getOriginalFilename()))) {
			throw new UnrecognizedStatementTypeException("Uploaded customer statement type is not supported. Only csv/xml type supported.");
		}else if (inputStatement.isEmpty()) {
			throw new TransactionsNotFoundException("Uploaded customer statement is empty.");
		} else {
			try {
				FailedTransactionReport theReport = statmentService.processStatement(inputStatement);
				headers.add("Processed -", inputStatement.getOriginalFilename());
				return new ResponseEntity<FailedTransactionReport>(theReport, headers, HttpStatus.OK);
			} catch (final Exception inEx) {
				throw new CustomerStatementProcessingException(inEx);
			}
		}
	}
}
