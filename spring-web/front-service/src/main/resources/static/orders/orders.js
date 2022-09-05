angular.module('store-front').controller('orderController', function ($scope, $rootScope, $http, $localStorage) {

    const gatewayPath = 'http://localhost:5000';
    var isCompleted = false;

    $scope.checkOut = function () {
        var data = {
            "cartName":$localStorage.cartName,
            "address":$scope.orderDetails.address,
            "phone":$scope.orderDetails.phone,
        }
        $http.post(gatewayPath + '/core/api/v1/order', data)
            .then(function (response) {
                $scope.loadCart();
                isCompleted = true;
            });
    }

    $rootScope.isUserLoggedIn = function () {
        if ($localStorage.springWebUser) {
            return true;
        } else {
            return false;
        }
    };

    $scope.disabledCheckOut = function () {
        alert("Для оформления заказа необходимо войти в учетную запись");
    }

    $scope.loadCart = function () {
        $http.post(gatewayPath + '/core/api/v1/carts', $localStorage.cartName)
            .then(function (response) {
                $scope.Cart = response.data;
            });
    }

    $scope.loadCart();

});