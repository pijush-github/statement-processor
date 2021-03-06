# Customer's monthly statement processing
This is a Spring MVC REST Service based backend application. A file upload service has exposed to consume a predefined template 
based csv & xml file as a monthly statement of customer and validate all transctions. The validation identifies all duplicate transaction
as failed and other transactions as failed whose end balance is not found correct based on the coalculation of the start balance & transactional
amount. This service respond back the message having all failed transaction & duplicate transaction count.

# Validation logic

	Duplicate Check - Found duplicate records based on the reference number of processed transaction records.
	Valid Transaction Check - The addition of Start balance & (+ve)Transactional balance or (-ve)Transactional balance is always equals 
	with end balance.

# Steps to Setup

1. Clone the repository

	git clone https://github.com/pijush-github/statement-processor.git

2. Build the app using maven. Execute below command from the project root directory (statement-processor)

	mvn clean install

3. Run the app using maven. Execute below command from the project root directory (statement-processor)

	mvn jetty:run

	That's it! The service can be accessed through Postman client with followings -
	Url : http://localhost:8080/statement-processor/transactional/monthly/statement.
	form-data : key=customerStatement value=<browse_input_file> 
	(sample files records.csv, records.xml can found under project's test/resources)
	
	SAMPLE OUTPUT:
	{
    		"failedTransactions": [
		{
		    "transactionReference": 154270,
		    "transactionDescription": "Candy for Peter de Vries"
		}
    		],
    		"noumberOfFailedTransactions": "1",
    		"processedStatementMessage": "All 2 transactions are validated from uploaded statement records.xml. No duplicate 			transactions were available."
	}
