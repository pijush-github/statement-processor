package com.rabo.customer.statement.processor.rest;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rabo.customer.statement.processor.bean.FailedTransactionReport;
import com.rabo.customer.statement.processor.bean.Report;
import com.rabo.customer.statement.processor.error.CustomerStatementProcessingException;
import com.rabo.customer.statement.processor.error.TransactionsNotFoundException;
import com.rabo.customer.statement.processor.error.UnrecognizedStatementTypeException;
import com.rabo.customer.statement.processor.service.StatementProcessorService;
import com.rabo.customer.statement.processor.service.StatementReportService;

@RestController
@RequestMapping(value = "/transactional")
public class StatementProcessingController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StatementProcessingController.class);

	@Value("${file.types.supported}")
	private String filetTypesSupported;
	
	@Autowired
	private StatementProcessorService statmentService;
	
	@Autowired
	private StatementReportService reportService;
	

	@RequestMapping(value = "/monthly/statement", headers = ("content-type=multipart/*"), method = RequestMethod.POST)
	public ResponseEntity<FailedTransactionReport> processMonthlyTransactions(
			@RequestParam("customerStatement") MultipartFile inputStatement) {
		HttpHeaders headers = new HttpHeaders();
		final String uploadedFileName = StringUtils.cleanPath(inputStatement.getOriginalFilename());
		if (!Arrays.asList(filetTypesSupported.split(",")).contains(FilenameUtils.getExtension(uploadedFileName))) {
			throw new UnrecognizedStatementTypeException(
					"Uploaded customer statement type is not supported. Only csv/xml type supported.");
		} else if (inputStatement.isEmpty()) {
			throw new TransactionsNotFoundException("Uploaded customer statement is empty.");
		} else {
			try {
				FailedTransactionReport theReport = statmentService.processStatement(inputStatement);
				final Long theReportId = reportService.generateReport(theReport, uploadedFileName);
				theReport.setTransactionProcessingReportId(theReportId);
				headers.add("Processed -", inputStatement.getOriginalFilename());
				headers.add("Report Id -", String.valueOf(theReportId));
				return new ResponseEntity<FailedTransactionReport>(theReport, headers, HttpStatus.OK);
			} catch (final Exception inEx) {
				LOGGER.error("Error occurred at StatementProcessingController.processMonthlyTransactions(): {0}", inEx);
				throw new CustomerStatementProcessingException(inEx);
			}
		}
	}

	@RequestMapping(value = "/monthly/statement/report/{reportId}", method = RequestMethod.GET, produces = "application/pdf")
	public ResponseEntity<InputStreamResource> downloadTransactionReport(@PathVariable("reportId") String reportId) {
		HttpHeaders headers = new HttpHeaders();
		try {
			Resource resource = reportService.loadFileAsResource(reportId);	
			headers.setContentType(MediaType.parseMediaType("application/pdf"));
			headers.add("Access-Control-Allow-Origin", "*");
			headers.add("Access-Control-Allow-Methods", "GET, POST, PUT");
			headers.add("Access-Control-Allow-Headers", "Content-Type");
			headers.add("Content-Disposition", "filename=" + reportId+".pdf");
			headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
			headers.add("Pragma", "no-cache");
			headers.add("Expires", "0");
			headers.setContentLength(resource.contentLength());
			ResponseEntity<InputStreamResource> response = new ResponseEntity<InputStreamResource>(
					new InputStreamResource(resource.getInputStream()), headers, HttpStatus.OK);
			return response;			
		} catch (final Exception inEx) {
			LOGGER.error("Error occurred at StatementProcessingController.downloadTransactionReport(): {0}", inEx);
			throw new CustomerStatementProcessingException(inEx);
		}

	}
	
	@RequestMapping(value = "/monthly/statement/reports", method = RequestMethod.GET)
	public ResponseEntity<List<Report>> getAvailableReports() {
		HttpHeaders headers = new HttpHeaders();
		List<Report> allReports = reportService.findAllAvailableReports();
		ResponseEntity<List<Report>> response = new ResponseEntity<List<Report>>(allReports, headers, HttpStatus.OK);
		return response;
	}

}
