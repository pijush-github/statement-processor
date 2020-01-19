package com.rabo.customer.statement.processor.service;

import static com.rabo.customer.statement.processor.aid.StatementProcessingConstatnts.CSV_FILE_EXTENSION;
import static com.rabo.customer.statement.processor.aid.StatementProcessingConstatnts.XML_FILE_EXTENSION;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.rabo.customer.statement.processor.bean.FailedTransactionInfo;
import com.rabo.customer.statement.processor.bean.FailedTransactionReport;
import com.rabo.customer.statement.processor.bean.TransactionRecord;
import com.rabo.customer.statement.processor.util.CSVStatementReader;
import com.rabo.customer.statement.processor.util.MonthlyStatementParser;
import com.rabo.customer.statement.processor.util.XMLStatementReader;

@Service
public class StatementProcessorService {

	private static final Logger LOGGER = LoggerFactory.getLogger(StatementProcessorService.class);

	@Autowired
	private MessageSource messageSource;

	public FailedTransactionReport processStatement(final MultipartFile inputStatement) throws IOException, Exception {
		LOGGER.info("Statment processing started");
		FailedTransactionReport txnReport = new FailedTransactionReport();
		txnReport.setFailedTransactions(new ArrayList<>());
		final MonthlyStatementParser theStatmentParser = new MonthlyStatementParser();
		theStatmentParser.registerFileReader(new CSVStatementReader(), CSV_FILE_EXTENSION);
		theStatmentParser.registerFileReader(new XMLStatementReader(), XML_FILE_EXTENSION);
		final String theFileExtension = FilenameUtils.getExtension(inputStatement.getOriginalFilename());
		final List<String> processedValidTxn = new ArrayList<>();
		Iterator<TransactionRecord> theTransactions = theStatmentParser.parse(inputStatement.getInputStream(),
				theFileExtension);
		int totalTxnCount = 0;
		int duplicateTxnCount = 0;
		while (theTransactions.hasNext()) {
			TransactionRecord theTxn = theTransactions.next();
			totalTxnCount++;
			if (CollectionUtils.contains(processedValidTxn.iterator(), theTxn.getReference().toString())) {
				FailedTransactionInfo theFailedTxn = new FailedTransactionInfo();
				duplicateTxnCount++;
				theFailedTxn.setTransactionReference(theTxn.getReference());
				theFailedTxn.setTransactionDescription(theTxn.getDescription());
				txnReport.getFailedTransactions().add(theFailedTxn);
			} else {
				LOGGER.info("Transaction validation started");
				if (isValidTransaction(theTxn)) {
					processedValidTxn.add(theTxn.getReference().toString());
				} else {
					FailedTransactionInfo theFailedTxn = new FailedTransactionInfo();
					theFailedTxn.setTransactionReference(theTxn.getReference());
					theFailedTxn.setTransactionDescription(theTxn.getDescription());
					txnReport.getFailedTransactions().add(theFailedTxn);
				}
				LOGGER.info("Transaction validation completed");
			}

		}
		txnReport.setNoumberOfFailedTransactions(String.valueOf(txnReport.getFailedTransactions().size()));
		if (duplicateTxnCount == 0) {
			txnReport.setProcessedStatementMessage(
					String.format(messageSource.getMessage("validation.msg.without.duplicate",
							new Object[] { String.valueOf(totalTxnCount), inputStatement.getOriginalFilename() },
							Locale.ENGLISH)));
		} else {
			txnReport.setProcessedStatementMessage(String.format(messageSource.getMessage(
					"validation.msg.with.duplicate", new Object[] { String.valueOf(totalTxnCount),
							inputStatement.getOriginalFilename(), String.valueOf(duplicateTxnCount) },
					Locale.ENGLISH)));
		}
		LOGGER.info("Statment processing ended");
		return txnReport;
	}

	private boolean isValidTransaction(final TransactionRecord inTxn) {
		BigDecimal theTransctionalBalance = inTxn.getStartBalance().add(inTxn.getMutation());
		if (theTransctionalBalance.compareTo(inTxn.getEndBalance()) == 0) {
			return true;
		} else {
			return false;
		}
	}
}
