App.controller('statementController',
    ['$scope', '$rootScope', 'statementService', '$http', function ($scope, $rootScope, statementService, $http) {

        $scope.file = '';
        $scope.message = '';
        $scope.reportId = '';

        $scope.upload = function () {
            var file = $scope.file;
            statementService.uploadStatement(file)
                .then(
                    function (response) {
                    	$scope.message = response.processedStatementMessage;
                    	$scope.reportId = response.transactionProcessingReportId;
                        alert("file uploaded successfully.");
                        $http.get("http://localhost:8080/statement-processor/transactional/monthly/statement/reports").success(
                            function (response) {
                                $rootScope.reportList = response;
                            });
                    },
                    function (errResponse) {

                    }
                );
        }
    }
    ]);

App.constant('urls', {
    DOC_URL: 'http://localhost:8080/statement-processor/transactional/monthly/statement'
});

App.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function (scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;

            element.bind('change', function () {
                scope.$apply(function () {
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]);

App.run(function ($rootScope, $http) {
    $http.get("http://localhost:8080/statement-processor/transactional/monthly/statement/reports").success(
        function (response) {
            $rootScope.reportList = response;
        });
});
