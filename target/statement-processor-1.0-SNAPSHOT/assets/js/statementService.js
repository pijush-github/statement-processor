'use strict';
var App = angular.module('statementUploadApp', []);

App.factory('statementService', ['$http', '$q', 'urls', function ($http, $q, urls) {

    var factory = {
    	uploadStatement: uploadStatement,
        findReport: findReport
    };

    return factory;

    function uploadStatement(file) {
        var deferred = $q.defer();
        var formData = new FormData();
        formData.append('customerStatement', file);

        $http.post(urls.DOC_URL, formData, {
            transformRequest: angular.identity,
            headers: {
                'Content-Type': undefined
            }
        })
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function (errResponse) {
                    alert(errResponse.data.errorMessage);
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function findReport(reportId) {
        var deferred = $q.defer();
        $http.get(urls.DOC_URL + '/report/' + reportId)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function (errResponse) {
                    alert(errResponse.data.errorMessage);
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }
}
]);