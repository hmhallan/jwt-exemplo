(function(){
	
	angular.module('exemplo.controllers').controller('HomeController', ['$scope','AuthenticationFactory', function($scope, AuthenticationFactory){
		
        $scope.logado = AuthenticationFactory.user();
		
	}]);
	
}).call(this);