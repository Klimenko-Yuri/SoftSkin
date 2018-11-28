/**
 * Created by mihail on 10/12/18.
 */

var app = angular.module('pp', []);

var host = "/skin-expert";


app.controller('parse', function ($scope, $http) {

    $scope.waitInfoShow =
        $scope.showFind = false;

    var config = {
        headers: {
            'Content-Type': undefined
        },
        transformRequest: angular.identity
    };

    $scope.getParseResult = function () {

        $scope.components = $scope.handleComponents = "";
        //this may be any post or interesting info
        $scope.waitInfo = "Идет сканирование картинки...";
        $scope.waitInfoShow = true;
        var f = document.getElementById('photo').files[0];

        var params = new FormData;
        params.append('photo', f);
        $scope.dataview = "nothing to show";

        $http.post(host + "/parse", params, config)
            .then(function (response) {
                $scope.components = response.data;
                if ($scope.components.length > 0) {
                    $scope.waitInfoShow = false;
                }
                else {
                    $scope.waitInfo = "Не распозналось ничего интересного"
                }
                $scope.showFind = true;
            });
    };

    //handle multiply find
    $scope.find = function () {
        $http.get(host + "/get-list-of-component?list=" + $scope.comstr)
            .then(function (response) {
                $scope.handleComponents = response.data;
            });
    }
});