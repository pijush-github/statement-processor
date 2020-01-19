package com.rabo.customer.statement.processor.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.rabo.customer.statement.processor.bean.FailedTransactionInfo;
import com.rabo.customer.statement.processor.bean.FailedTransactionReport;
import com.rabo.customer.statement.processor.bean.Report;
import com.rabo.customer.statement.processor.error.ReportNotFoundException;
import com.rabo.customer.statement.processor.error.ReportStorageException;

@Service
public class StatementReportService {

	private static final Logger LOGGER = LoggerFactory.getLogger(StatementReportService.class);

	@Value("${file.upload.location}")
	private String filetUploadLocation;

	public Long generateReport(final FailedTransactionReport inReport, final String inUploadedFileName) throws Exception {
		createDirectoryIfNotExist();
		Document reportDocument = new Document();
		ByteArrayOutputStream theOutputStream = new ByteArrayOutputStream();
		final Long theReportId = Long.valueOf(System.currentTimeMillis());
		try {

			PdfPTable table = new PdfPTable(2);
			table.setWidthPercentage(80);
			table.setWidths(new int[] { 3, 3 });

			Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

			PdfPCell hcell;
			hcell = new PdfPCell(new Phrase("Transaction Reference", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Transaction Description", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			for (FailedTransactionInfo txn : inReport.getFailedTransactions()) {

				PdfPCell cell;

				cell = new PdfPCell(new Phrase(txn.getTransactionReference().toString()));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(txn.getTransactionDescription().toString()));
				cell.setPaddingLeft(5);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
			}

			PdfWriter writer = PdfWriter.getInstance(reportDocument, theOutputStream);
			reportDocument.open();
			reportDocument.add(table);
			reportDocument.close();
			writer.close();
			
			final Path targetLocation = Paths.get(filetUploadLocation).toAbsolutePath().normalize().resolve(theReportId + ".pdf");
			Files.copy(new ByteArrayInputStream(theOutputStream.toByteArray()), targetLocation,
					StandardCopyOption.REPLACE_EXISTING);

		} catch (DocumentException ex) {
			LOGGER.error("Error occurred at StatementReportService.generateReport(): {0}", ex);
			throw new ReportStorageException("Could not abale to create the report after processing the customer statement.");
		}

		return theReportId;
	}

	public Resource loadFileAsResource(final String reportId) {
		createDirectoryIfNotExist();
		final Path resourceLocation = Paths.get(filetUploadLocation).toAbsolutePath().normalize().resolve(reportId+".pdf").normalize();
        try {
            Resource resource = new UrlResource(resourceLocation.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new ReportNotFoundException("Report not found "+ reportId);
            }
        } catch (final MalformedURLException ex) {
        	LOGGER.error("Error occurred at StatementReportService.loadFileAsResource(): {0}", ex);
            throw new ReportNotFoundException("The requested Report with report id: "+ reportId +" is not found");
        }
    }

	public List<Report> findAllAvailableReports() {
		createDirectoryIfNotExist();
		final Path reportStorageLocation = Paths.get(filetUploadLocation).toAbsolutePath().normalize();
		List<Report> reportList = new ArrayList<>();
		try (Stream<Path> walk = Files.walk(reportStorageLocation)) {
			List<String> result = walk.map(x -> x.getFileName().toString())
					.filter(f -> f.endsWith(".pdf")).collect(Collectors.toList());
			for(String report : result) {
				reportList.add(new Report(report.substring(0,report.lastIndexOf(".pdf")), report));
			}
		} catch (final IOException e) {
			LOGGER.error("Error occurred at StatementReportService.findAllAvailableReports(): {0}", e);
			 throw new ReportNotFoundException("No Report is available for any processed customer statement");
		}
		return reportList;
	}
	
	private void createDirectoryIfNotExist() {
		try {
			boolean isDirExists = Files.exists(Paths.get(filetUploadLocation));
			if(!isDirExists) {
				Files.createDirectories(Paths.get(filetUploadLocation).toAbsolutePath().normalize());
			}
        } catch (final IOException ex) {
        	LOGGER.error("Error occurred at StatementReportService.createDirectoryIfNotExist(): {0}", ex);
            throw new ReportStorageException("Could not create the directory where the uploaded files will be stored.");
        }
	}
}
