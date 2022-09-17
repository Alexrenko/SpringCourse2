angular.module('store-front').controller('cartController',
    function ($scope, $rootScope, $http, $localStorage) {

    const gatewayPath = 'http://localhost:5000';

    $scope.loadCart = function () {
        $http.post(gatewayPath + '/cart/api/v1/carts', $localStorage.cartName)
            .then(function (response) {
                $scope.Cart = response.data;

            });
    }

    $scope.clearCart = function () {
        $http.post(gatewayPath + '/cart/api/v1/carts/clear', $localStorage.cartName)
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
        $http.post(gatewayPath + '/core/api/v1/order', data)
            .then(function (response) {
                $scope.loadCart();
                location.href = '#/orders.html';
                $scope.orderDetails = null;
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