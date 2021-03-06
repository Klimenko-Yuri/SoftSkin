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

app.controller('clicker', function ($scope, $http) {
    $scope.find = function () {
        $scope.msg = '';
        $scope.components = null;
        $http.get(host + "/find-component/" + $scope.name)
            .then(function (response) {
                components = response.data;
                if (components.length > 0) {
                    $scope.components = components;
                    $scope.id = components[0].id;
                    $scope.name = components[0].name;
                    $scope.nameENG = components[0].nameENG;
                    $scope.description = components[0].description;
                    $scope.type = components[0].type;
                    $scope.show = true; //this for angular ng-show
                } else {
                    $scope.msg = 'Компонент ' + $scope.name + ' не найден';
                    $scope.show = false;
                }
            });

    };

    $scope.add = function () {

        var params = {
            name: $scope.name,
            description: $scope.description,
            type: $scope.type,
            nameENG: $scope.nameENG,
            id: $scope.id + ''
        };

        $http.post(host + "/add-component", params)
            .then(function (response) {
                $scope.msg = response.data;
            });

    }
});

app.controller('get-all', function ($scope, $http) {
    $http.get(host + "/get-all-component")
        .then(function (response) {
            $scope.components = response.data;
        });
    $scope.del = function (id) {
        $http.delete(host + "/delete-component/" + id)
            .then(function (response) {
                $scope.msg = response.data;
            });
    }
});