<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html ng-app="statementUploadApp">
<head>
    	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Upload Monthly Transactions Detail</title>
        <link rel="stylesheet" type="text/css" href="<c:url value='/assets/css/bootstrap.min.css' />" />
        <script type="text/javascript" src="<c:url value='assets/lib/plugin/jquery-2.2.1.min.js'/>"></script>
		<script type="text/javascript" src="<c:url value='assets/lib/plugin/bootstrap.min.js'/>"></script>
		<script type="text/javascript" src="<c:url value='assets/lib/plugin/angular.min.js'/>"></script>
		<script type="text/javascript" src="<c:url value='assets/js/statementService.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/assets/app.js'/>"></script>        
</head>
<body ng-controller="statementController">
<div class="container">
    <div class="col-md-6">
    	<h2>Upload Customer's Monthly Statement</h2>
        <div class="row">
            <table class="table table-bordered">
                <tr>
                    <td><input type="file" class="custom-file-input" file-model="file"></td>
                </tr>
                <tr>
                    <td>
                        <button ng-click="upload()">Upload</button>
                    </td>
                 </tr>
                 <tr ng-if="reportId !== ''">
                    <td>
                    	<p style="font-weight:bold;">Generated Report Id: {{reportId}}</p>
                    </td>
                </tr>
            </table>
        </div>
        <div class="row">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>Statement Processing Report</th>
                    <th>Download Report</th>
                </tr>
                </thead>
                <tbody>
                <tr ng-repeat="report in reportList">
                    <td><a href="">{{report.reportName}}</a></td>
                    <td><a href="http://localhost:8080/statement-processor/transactional/monthly/statement/report/{{report.id}}" target="_blank">Download</a></td>
                </tr>
                </tbody>
            </table>
        </div>
        <p style="color:red;" ng-if="message !== ''">{{message}}</p>
    </div>
</div>
</body>
</html>
