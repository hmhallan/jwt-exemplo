(function(){
	
	angular.module('exemplo.controllers').controller('HomeController', ['$scope','AuthenticationFactory', '$http', 
	                                                            function($scope, AuthenticationFactory, $http){
		
        $scope.dadosUsuarioLocal = AuthenticationFactory.user();
        
        
        
        $http.get("./rest/usuario/dados").success(function(data){
        	$scope.dadosUsuarioRemoto = data;
        });
        
        $http.get("./rest/usuario/protegido")
        	.success(function(data,status){
        		$scope.dadosProtegido = data;
        		$scope.statusProtegido = status;
        	})
        	.error(function(data, status){
        		$scope.dadosProtegido = data;
        		$scope.statusProtegido = status;
        	});
        
		
	}]);
	
}).call(this);