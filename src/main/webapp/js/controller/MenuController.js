(function(){
	
	angular.module("exemplo.controllers").controller("MenuController",['$rootScope','$scope', '$location', 'UserAuthFactory', 'AuthenticationFactory', function($rootScope, $scope, $location, UserAuthFactory, AuthenticationFactory){
        
        //valor armazenado no storage
        $scope.isLogged = AuthenticationFactory.isLogged;
        if ($scope.isLogged){
        	$scope.nome = AuthenticationFactory.user().login;
		}
	
        console.info($scope.isLogged);
        
        //eventos do menu
        $scope.logout = function(){
            UserAuthFactory.logout();
            $rootScope.$broadcast('logoutEvent', { message: 'log off' });
        };
        
        //eventos de login e logout
        $scope.$on('loginEvent', function (event, args) {
            $scope.isLogged = true;
            $scope.nome = AuthenticationFactory.user().login;
        });
        $scope.$on('logoutEvent', function (event, args) {
            $scope.isLogged = false;
            $scope.nome = "";
        });
        
	}]);	
	
}).call(this);