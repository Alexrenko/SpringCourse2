angular.module('store-front').controller('cartController',
    function ($scope, $rootScope, $http, $localStorage) {

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

    $scope.checkOut = function () {
        var data = {
            "cartName":$localStorage.cartName,
            "address":$scope.orderDetails.address,
            "phone":$scope.orderDetails.phone,
        }
        $http.post('http://localhost:8189/app/api/v1/order', data)
            .then(function (response) {
                $scope.loadCart();
                location.href = '#/orders.html';
            });
    }

        $scope.goToOrders = function (){
            location.href = '#!/orders';
        };

    $rootScope.isUserLoggedIn = function () {
        if ($localStorage.springWebUser) {
            return true;
        } else {
            return false;
        }
    };

    $scope.loadCart();

});