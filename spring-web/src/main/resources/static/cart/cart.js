angular.module('store-front').controller('cartController', function ($scope, $rootScope, $http, $localStorage) {

    const contextPath = 'http://localhost:8189/app/api/v1';

    $scope.loadCart = function () {
        $http.post('http://localhost:8189/app/api/v1/carts', $localStorage.cartName)
            .then(function (response) {
                $scope.Cart = response.data;
            });
    }

    $scope.clearCart = function () {
        $http.post('http://localhost:8189/app/api/v1/carts/clear', $localStorage.cartName)
            .then(function (response) {
                $scope.loadCart();
            });
    }

    $scope.loadCart();

});