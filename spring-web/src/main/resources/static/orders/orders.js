angular.module('store-front').controller('orderController, cartController', function ($scope, $rootScope, $http, $localStorage) {

    const contextPath = 'http://localhost:8189/app/api/v1';

    $scope.checkOut = function () {
        var data = {
            "cartName":$localStorage.cartName,
            "address":$scope.orderDetails.address,
            "phone":$scope.orderDetails.phone
        };
        $http({
            url: 'http://localhost:8189/app/api/v1/order',
            method: 'POST',
            data: data
        }).then(function (response) {
            $scope.loadCart();
        });
    };


    $scope.disabledCheckOut = function () {
        alert("Для оформления заказа необходимо войти в учетную запись");
    }

});