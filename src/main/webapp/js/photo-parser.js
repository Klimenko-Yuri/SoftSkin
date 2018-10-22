/**
 * Created by mihail on 10/12/18.
 */

var app = angular.module('pp', ['ngFileUpload']);

var host = "/skin-expert";



app.controller('parse', function ($scope, $http) {

    var header_config = {
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    };

    var params = {
        photo : scope.photo
    };

    $scope.getParseResult = function () {
        $http.post(host + "/parse", params, header_config)
            .then(function (response) {
                $scope.components = response.data;
            });
    };
});
