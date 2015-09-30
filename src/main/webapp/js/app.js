(function(){
	
    angular.module('exemplo.controllers', []);
    angular.module('exemplo.services', []);

	var app = angular.module('jwt-exemplo', 
                             [ 'exemplo.auth',
                               'exemplo.controllers', 
                               'exemplo.services',
                               'ngRoute',
                               'ngStorage']);

    
    
    app.config(['$routeProvider', '$httpProvider', function($routeProvider, $httpProvider){
        
        //interceptor para envio de token (se existir)
        $httpProvider.interceptors.push('TokenInterceptor');
        
        var logged = {requiresLogin: true};
        var notLogged = {requiresLogin: false};

		$routeProvider
        .when('/home', {
            templateUrl: 'partials/home.html', 
            controller: 'HomeController',
            access: logged
        })
      
        .when('/login', {templateUrl: 'partials/login.html', controller: 'LoginController', access: notLogged})

        .otherwise ({ redirectTo: '/home' });
        
        

    

	}]);
    
    
    app.run(function($rootScope, $localStorage, $location, AuthenticationFactory) {
        // faz uma checagem local para ver se tem token
        var ok = AuthenticationFactory.check();
        if (ok){
            //emite a msg de login
            $rootScope.$broadcast('loginEvent', { message: 'Logou' });
        }
        
        //verifica se a proxima rota precisa de login e o usuario tem permissao para acessar
        $rootScope.$on("$routeChangeStart", function(event, nextRoute, currentRoute) {
            if ((nextRoute.access && nextRoute.access.requiresLogin) &&  !AuthenticationFactory.isLogged) {
              $location.path("/login");
            } else {
              // check if user object exists else fetch it. This is incase of a page refresh
              if (!AuthenticationFactory.user) AuthenticationFactory.user = $localStorage.user;
            }
        });
        
        $rootScope.$on('$routeChangeSuccess', function(event, nextRoute, currentRoute) {
            $rootScope.showMenu = AuthenticationFactory.isLogged;
            
            // if the user is already logged in, take him to the home page
            if (AuthenticationFactory.isLogged == true && $location.path() == '/login') {
              $location.path('/');
            }
          });
        
    });

	
}).call(this);