angular.module('store-front').controller('productController', function ($scope, $rootScope, $http, $localStorage) {

    const gatewayPath = 'http://localhost:5000';

    $scope.loadProducts = function (pageIndex = 1) {
        $http({
            url: gatewayPath + '/core/api/v1/products',
            method: 'GET',
            params: {
                title_part: $scope.filter ? $scope.filter.title_part : null,
                min_price: $scope.filter ? $scope.filter.min_price : null,
                max_price: $scope.filter ? $scope.filter.max_price : null
            }
        }).then(function (response) {
            $scope.ProductsPage = response.data
        });
    };

    $scope.addToCart = function (productId) {
        $http.post(gatewayPath + '/cart/api/v1/carts/add/' + productId, $localStorage.cartName)
            .then(function (response) {
                $scope.loadCart();
            });
    }

    $scope.loadProducts();

});