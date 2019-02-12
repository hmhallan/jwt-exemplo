(function(){

    angular.module('jwt.controllers', []);
    angular.module('jwt.services', []);

    var app = angular.module('jwt-exemplo', [ 'jwt.auth', 'jwt.controllers', 'jwt.services', 'ngRoute', 'ngStorage']);



    app.config(['$routeProvider', '$httpProvider', '$sessionStorageProvider', '$localStorageProvider', function($routeProvider, $httpProvider, $sessionStorageProvider, $localStorageProvider){

        $httpProvider.interceptors.push('TokenInterceptor');

        $sessionStorageProvider.setKeyPrefix('jwtexemplo-');
        $localStorageProvider.setKeyPrefix('jwtexemplo-');

        var logged = {requiresLogin: true};
        var notLogged = {requiresLogin: false};

        $routeProvider
            .when('/home', { templateUrl: 'modules/home/home.html', controller: 'HomeController', access: logged })

            .when('/login', {templateUrl: 'modules/login/login.html', controller: 'LoginController', access: notLogged})
            .otherwise ({ redirectTo: '/home' });





    }]);


    app.run(function($rootScope, $localStorage, $location, AuthenticationFactory) {
        var ok = AuthenticationFactory.check();
        if (ok){
            $rootScope.$broadcast('loginEvent', { message: 'Logou' });
        }

        $rootScope.$on("$routeChangeStart", function(event, nextRoute, currentRoute) {
            if ((nextRoute.access && nextRoute.access.requiresLogin) &&  !AuthenticationFactory.isLogged) {
                $location.path("/login");
            } else {
                if (!AuthenticationFactory.user) AuthenticationFactory.user = $localStorage.user;
            }
        });

        $rootScope.$on('$routeChangeSuccess', function(event, nextRoute, currentRoute) {
            $rootScope.showMenu = AuthenticationFactory.isLogged;

            if (AuthenticationFactory.isLogged == true && $location.path() == '/login') {
                $location.path('/');
            }
        });

    });


}).call(this);