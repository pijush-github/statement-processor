package com.rabo.customer.statement.processor.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandlerAdvice extends ResponseEntityExceptionHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandlerAdvice.class);
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception inEx) {
		return error(HttpStatus.INTERNAL_SERVER_ERROR, inEx);
	}
	
	@ExceptionHandler(MultipartException.class)
	public ResponseEntity<String> handleFileUploadException(MultipartException inMpartEx) {
		return error(HttpStatus.BAD_REQUEST, inMpartEx);
	}
	
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<String> handleMaxFileUploadException(MaxUploadSizeExceededException inMaxSizeEx) {
		return error(HttpStatus.BAD_REQUEST, inMaxSizeEx);
	}
	
	
	@ExceptionHandler({CustomerStatementProcessingException.class})
	public ResponseEntity<String> handleCustomerStatementProcessingException(CustomerStatementProcessingException inStProcEx) {
		return error(HttpStatus.INTERNAL_SERVER_ERROR, inStProcEx);
	}
	
	@ExceptionHandler({UnrecognizedStatementTypeException.class})
	public ResponseEntity<String> handleCustomerStatementTypeNotSupportedException(UnrecognizedStatementTypeException inNotSupportEx) {
		return error(HttpStatus.BAD_REQUEST, inNotSupportEx);
	}
	
	@ExceptionHandler({TransactionsNotFoundException.class})
	public ResponseEntity<String> handleCustomerStatementNotFoundException(TransactionsNotFoundException inNotFoundEx) {
		return error(HttpStatus.BAD_REQUEST, inNotFoundEx);
	}
	
	@ExceptionHandler({RuntimeException.class})
    public ResponseEntity<String> handleRunTimeException(final RuntimeException inRunEx) {
        return error(HttpStatus.INTERNAL_SERVER_ERROR, inRunEx);
    }
	
	private ResponseEntity<String> error(final HttpStatus status, final Exception inEx) {
		LOGGER.error("Exception : ", inEx);
        return ResponseEntity.status(status).body(inEx.getMessage());
    }

}
