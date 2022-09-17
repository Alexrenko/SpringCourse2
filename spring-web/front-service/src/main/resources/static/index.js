(function () {
    angular
        .module('store-front', ['ngRoute', 'ngStorage'])
        .config(config)
        .run(run);

    function config($routeProvider) {
        $routeProvider
            .when('/products', {
                templateUrl: 'products/products.html',
                controller: 'productController'
            })
            .when('/cart', {
                templateUrl: 'cart/cart.html',
                controller: 'cartController'
            })
            .when('/orders', {
                templateUrl: 'orders/orders.html',
                controller: 'orderController'
            })
            .otherwise({
                redirectTo: '/'
            });
    }

    function run($rootScope, $http, $localStorage) {
        if ($localStorage.springWebUser) {
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.springWebUser.token;
        }
    }
})();


angular.module('store-front').controller('indexController', function ($scope, $rootScope, $http, $localStorage) {

    const gatewayPath = 'http://localhost:5000';

    if(!$localStorage.cartName){
        $localStorage.cartName = "cart_" + (Math.random() * 100);
    }

    if ($localStorage.springWebUser) {
        $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.springWebUser.token;
        $localStorage.cartName = "cart_" + $localStorage.springWebUser.username;
    }

    $scope.tryToAuth = function () {
        $http.post(gatewayPath + '/auth/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.springWebUser = {username: $scope.user.username, token: response.data.token};

                    $localStorage.username = $scope.user.username;

                    $scope.user.username = null;
                    $scope.user.password = null;
                }
            }, function errorCallback(response) {
            });
    };

    $scope.tryToLogout = function () {
        $scope.clearUser();
        if ($scope.user.username) {
            $scope.user.username = null;
        }
        if ($scope.user.password) {
            $scope.user.password = null;
        }
    };

    $scope.clearUser = function () {
        delete $localStorage.springWebUser;
        delete $localStorage.cartName;
        $http.defaults.headers.common.Authorization = '';
    };

    $rootScope.isUserLoggedIn = function () {
        if ($localStorage.springWebUser) {
            return true;
        } else {
            return false;
        }
    };

    $scope.showCurrentUserInfo = function () {
        $http.get(gatewayPath + '/core/api/v1/profile')
            .then(function successCallback(response) {
                alert('MY NAME IS: ' + response.data.username);
            }, function errorCallback(response) {
                alert('UNAUTHORIZED');
            });
    }

});