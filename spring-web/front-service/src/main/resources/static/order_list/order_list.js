angular.module('store-front').controller('orderListController', function ($scope, $rootScope, $http, $localStorage) {

    const gatewayPath = 'http://localhost:5000';

    $scope.loadOrders = function () {
        $http.post(gatewayPath + '/core/api/v1/order')
        .then(function (response) {
            $scope.orders = response.data
        });
    };

    $scope.loadOrders();
});
