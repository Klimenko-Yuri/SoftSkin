/**
 * Created by mihoj on 10.08.18.
 */

var app = angular.module('cm', []);

var host = "/skin-expert";

/**
 * in future may response all back and attribute
 */
app.controller('action-message', function ($scope, $http) {
    $http.get(host + "/get-back-attr")
        .then(function (response) {
            $scope.message = response.data;
        });
});

app.controller('finder', function ($scope, $http) {
    $scope.find = function() {
        $scope.show = true;
        $http.post(host + "/find-component/" + $scope.name)
            .then(function (response) {
                $scope.msg = response.data;
                if($scope.msg != null) {
                    $scope.add = true;
                }
            });

    }
});