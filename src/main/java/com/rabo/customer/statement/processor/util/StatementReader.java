package com.rabo.customer.statement.processor.util;

import java.io.InputStream;
import java.util.List;

import com.rabo.customer.statement.processor.bean.TransactionRecord;

public interface StatementReader {

	public List<TransactionRecord> read(InputStream inputStream) throws Exception;
}
